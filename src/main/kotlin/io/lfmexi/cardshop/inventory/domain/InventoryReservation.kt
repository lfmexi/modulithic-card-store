package io.lfmexi.cardshop.inventory.domain

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId
import java.time.Instant

data class InventoryReservation(
    val shopId: ShopId,
    val orderId: OrderId,
    val productId: ProductId,
    val orderedQuantity: Quantity,
    val confirmedQuantity: Quantity,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    val pendingQuantity: Quantity = orderedQuantity - confirmedQuantity

    companion object {
        fun create(command: ReserveInventoryCommand): InventoryReservation {
            return InventoryReservation(
                shopId = command.shopId,
                orderId = command.orderId,
                productId = command.productId,
                orderedQuantity = command.orderedQuantity,
                confirmedQuantity = Quantity.ZERO,
                createdAt = Instant.now(),
                updatedAt = Instant.now(),
            )
        }
    }
}

data class ReserveInventoryCommand(
    val shopId: ShopId,
    val productId: ProductId,
    val orderId: OrderId,
    val orderedQuantity: Quantity,
)

sealed class InventoryReservationResult {
    data class Success(val inventory: InventoryReservation) : InventoryReservationResult()
    data class Failure(val reason: String) : InventoryReservationResult()
}