package io.lfmexi.cardshop.order.domain

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.Price
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId
import java.time.Instant
import java.util.Currency

sealed interface Order {
    val id: OrderId
    val shopId: ShopId
    val productId: ProductId
    val state: OrderState
    val orderedQuantity: Quantity
    val confirmedQuantity: Quantity
    val orderedPrice: Price
    val currency: Currency
    val lastConfirmedPrice: Price?
    val createdAt: Instant
    val updatedAt: Instant

    fun canBeRejected(): Boolean = state in setOf(OrderState.CREATED, OrderState.REQUESTED, OrderState.PENDING)

    fun canBeRequested(): Boolean = state in setOf(OrderState.CREATED)

    fun reject(): Order?

    fun request(): Order? = null

    companion object {
        fun createOrder(request: CreateOrderCommand): OrderCreationResult {
            return when (request) {
                is CreateBuyOrderCommand -> createBuyOrder(request)
                is CreateSellOrderCommand -> createSellOrder(request)
            }
        }

        private fun createBuyOrder(request: CreateBuyOrderCommand): OrderCreationResult {
            return when {
                request.orderedQuantity <= Quantity.ZERO -> OrderCreationResult.Failure
                else -> with(request) {
                    OrderCreationResult.Success(
                        BuyOrder(
                            id = OrderId.generate(),
                            shopId = shopId,
                            productId = productId,
                            state = OrderState.CREATED,
                            orderedQuantity = orderedQuantity,
                            orderedPrice = orderedPrice,
                            currency = currency,
                            confirmedQuantity = Quantity.ZERO,
                            createdAt = Instant.now(),
                            updatedAt = Instant.now(),
                        )
                    )
                }
            }
        }

        private fun createSellOrder(request: CreateSellOrderCommand): OrderCreationResult {
            return when {
                request.orderedQuantity >= Quantity.ZERO -> OrderCreationResult.Failure
                else -> with(request) {
                    OrderCreationResult.Success(
                        SellOrder(
                            id = OrderId.generate(),
                            shopId = shopId,
                            productId = productId,
                            state = OrderState.CREATED,
                            orderedQuantity = orderedQuantity,
                            orderedPrice = orderedPrice,
                            currency = currency,
                            confirmedQuantity = Quantity.ZERO,
                            createdAt = Instant.now(),
                            updatedAt = Instant.now(),
                        )
                    )
                }
            }
        }
    }
}

data class BuyOrder(
    override val id: OrderId,
    override val shopId: ShopId,
    override val productId: ProductId,
    override val state: OrderState,
    override val orderedQuantity: Quantity,
    override val confirmedQuantity: Quantity,
    override val orderedPrice: Price,
    override val currency: Currency,
    override val lastConfirmedPrice: Price? = null,
    override val createdAt: Instant,
    override val updatedAt: Instant,
): Order {
    init {
        check(orderedQuantity > Quantity.ZERO)
    }

    override fun reject(): Order? {
        return this.takeIf { it.canBeRejected() }
            ?.copy(
                state = OrderState.REJECTED,
                updatedAt = Instant.now(),
            )
    }

    override fun request(): Order? {
        return this.takeIf { it.canBeRequested() }
            ?.copy(
                state = OrderState.REJECTED,
                updatedAt = Instant.now(),
            )
    }
}

data class SellOrder(
    override val id: OrderId,
    override val shopId: ShopId,
    override val productId: ProductId,
    override val state: OrderState,
    override val orderedQuantity: Quantity,
    override val confirmedQuantity: Quantity,
    override val orderedPrice: Price,
    override val currency: Currency,
    override val lastConfirmedPrice: Price? = null,
    override val createdAt: Instant,
    override val updatedAt: Instant,
): Order {
    init {
        check(orderedQuantity < Quantity.ZERO)
    }

    override fun reject(): Order? {
        return this.takeIf { it.canBeRejected() }
            ?.copy(
                state = OrderState.REJECTED,
                updatedAt = Instant.now(),
            )
    }

    override fun request(): Order? {
        return this.takeIf { it.canBeRequested() }
            ?.copy(
                state = OrderState.REJECTED,
                updatedAt = Instant.now(),
            )
    }
}

enum class OrderState {
    CREATED,
    REQUESTED,
    PENDING,
    REJECTED,
    CANCELLED,
    COMPLETED,
}

sealed class OrderCreationResult {
    data class Success(val order: Order) : OrderCreationResult()
    object Failure : OrderCreationResult()
}

sealed class OrderRequestResult {
    data class Success(val requestedOrder: Order) : OrderRequestResult()
    object Failure : OrderRequestResult()
}