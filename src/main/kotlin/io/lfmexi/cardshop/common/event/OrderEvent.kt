package io.lfmexi.cardshop.common.event

import io.lfmexi.cardshop.common.domain.*
import org.jmolecules.event.types.DomainEvent
import java.time.Instant

sealed interface OrderEvent : DomainEvent {
    val orderId: OrderId
    val shopId: ShopId
    val productId: ProductId
    val state: String
    val orderedQuantity: Quantity
    val confirmedQuantity: Quantity
    val orderedPrice: Price
    val currency: String
    val lastConfirmedPrice: Price?
    val occurredAt: Instant
}

data class OrderRequested(
    override val orderId: OrderId,
    override val shopId: ShopId,
    override val productId: ProductId,
    override val state: String,
    override val orderedQuantity: Quantity,
    override val confirmedQuantity: Quantity,
    override val orderedPrice: Price,
    override val currency: String,
    override val lastConfirmedPrice: Price?,
    override val occurredAt: Instant
): OrderEvent
