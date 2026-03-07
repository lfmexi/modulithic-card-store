package io.lfmexi.cardshop.order.adapters.persistence.dao

import io.lfmexi.cardshop.common.domain.*
import io.lfmexi.cardshop.order.domain.BuyOrder
import io.lfmexi.cardshop.order.domain.Order
import io.lfmexi.cardshop.order.domain.OrderState
import io.lfmexi.cardshop.order.domain.SellOrder
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    val id: String,
    val shopId: String,
    val productId: String,
    @Enumerated(EnumType.STRING)
    val state: OrderState,
    @Enumerated(EnumType.STRING)
    val type: OrderType,
    val orderedQuantity: Long,
    val confirmedQuantity: Long,
    val orderedPrice: BigDecimal,
    val currency: Currency,
    val lastConfirmedPrice: BigDecimal? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun toDomain(): Order {
        return when (type) {
            OrderType.BUY -> BuyOrder(
                id = OrderId(id),
                shopId = ShopId(shopId),
                productId = ProductId(productId),
                state = state,
                orderedQuantity = Quantity(orderedQuantity),
                confirmedQuantity = Quantity(confirmedQuantity),
                currency = currency,
                orderedPrice = Price(orderedPrice),
                lastConfirmedPrice = lastConfirmedPrice?.let { Price(it) },
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
            OrderType.SELL -> SellOrder(
                id = OrderId(id),
                shopId = ShopId(shopId),
                productId = ProductId(productId),
                state = state,
                orderedQuantity = Quantity(orderedQuantity),
                confirmedQuantity = Quantity(confirmedQuantity),
                currency = currency,
                orderedPrice = Price(orderedPrice),
                lastConfirmedPrice = lastConfirmedPrice?.let { Price(it) },
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }

    companion object {
        fun from(order: Order): OrderEntity {
            return OrderEntity(
                id = order.id.value,
                shopId = order.shopId.value,
                productId = order.productId.value,
                state = order.state,
                orderedQuantity = order.orderedQuantity.value,
                confirmedQuantity = order.confirmedQuantity.value,
                orderedPrice = order.orderedPrice.value,
                currency = order.currency,
                lastConfirmedPrice = order.lastConfirmedPrice?.value,
                createdAt = order.createdAt,
                updatedAt = order.updatedAt,
                type = when (order) {
                    is BuyOrder -> OrderType.BUY
                    is SellOrder -> OrderType.SELL
                }
            )
        }
    }
}

enum class OrderType {
    BUY,
    SELL,
}