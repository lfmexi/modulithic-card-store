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

    fun canBeRejected(): Boolean = state == OrderState.PENDING

    fun reject(): Order?

    companion object {
        fun createOrder(request: CreateOrderCommand): OrderRequestResult {
            return when (request) {
                is CreateBuyOrderCommand -> createBuyOrder(request)
                is CreateSellOrderCommand -> createSellOrder(request)
            }
        }

        private fun createBuyOrder(request: CreateBuyOrderCommand): OrderRequestResult {
            return when {
                request.orderedQuantity <= Quantity.ZERO -> OrderRequestResult.Failure
                else -> with(request) {
                    OrderRequestResult.Success(
                        BuyOrder(
                            id = OrderId.generate(),
                            shopId = shopId,
                            productId = productId,
                            state = OrderState.PENDING,
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

        private fun createSellOrder(request: CreateSellOrderCommand): OrderRequestResult {
            return when {
                request.orderedQuantity >= Quantity.ZERO -> OrderRequestResult.Failure
                else -> with(request) {
                    OrderRequestResult.Success(
                        SellOrder(
                            id = OrderId.generate(),
                            shopId = shopId,
                            productId = productId,
                            state = OrderState.PENDING,
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
    override fun reject(): Order? {
        return this.takeIf { it.canBeRejected() }
            ?.copy(
                state = OrderState.REJECTED,
                updatedAt = Instant.now(),
            )
    }

    init {
        check(orderedQuantity < Quantity.ZERO)
    }
}

enum class OrderState {
    PENDING,
    REJECTED,
    CANCELLED,
    COMPLETED,
}

sealed class OrderRequestResult {
    data class Success(val order: Order) : OrderRequestResult()
    object Failure : OrderRequestResult()
}