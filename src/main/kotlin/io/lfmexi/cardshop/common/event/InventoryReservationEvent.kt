package io.lfmexi.cardshop.common.event

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.domain.InventoryReservation
import org.jmolecules.event.types.DomainEvent
import java.time.Instant

sealed interface InventoryReservationEvent : DomainEvent {
    val shopId: ShopId
    val orderId: OrderId
    val productId: ProductId
    val orderedQuantity: Quantity
    val confirmedQuantity: Quantity
    val occurredAt: Instant
}

data class InventoryReservationCreated(
    override val shopId: ShopId,
    override val orderId: OrderId,
    override val productId: ProductId,
    override val orderedQuantity: Quantity,
    override val confirmedQuantity: Quantity,
    override val occurredAt: Instant,
) : InventoryReservationEvent

data class InventoryReservationRejected(
    override val shopId: ShopId,
    override val orderId: OrderId,
    override val productId: ProductId,
    override val orderedQuantity: Quantity,
    override val confirmedQuantity: Quantity,
    override val occurredAt: Instant,
    val reason: String,
) : InventoryReservationEvent
