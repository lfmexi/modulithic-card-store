package io.lfmexi.cardshop.inventory.domain

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId

data class Inventory (
    val shopId: ShopId,
    val inventoryItems: Map<ProductId, InventoryItem>,
) {
    fun validate(reservation: InventoryReservation): InventoryValidationResult {
        val item = inventoryItems[reservation.productId] ?: InventoryItem.default(reservation.productId)

        return when {
            reservation.productId != item.productId || shopId != reservation.shopId -> InventoryValidationResult.Invalid
            item.confirmedQuantity + reservation.orderedQuantity < Quantity.ZERO -> InventoryValidationResult.Invalid
            else -> InventoryValidationResult.Valid
        }
    }

    companion object {
        fun load(shopId: ShopId, reservations: List<InventoryReservation>): Inventory {
            return Inventory(
                shopId = shopId,
                inventoryItems = reservations.groupBy { it.productId }
                    .map { (productId, reservations) ->
                        productId to InventoryItem(
                            productId = productId,
                            confirmedQuantity = reservations.map { it.confirmedQuantity }
                                .reduce { acc, current -> acc + current },
                            pendingQuantity = reservations.map { it.pendingQuantity }
                                .reduce { acc, current -> acc + current }
                        )
                    }.toMap()
            )
        }
    }
}

data class InventoryItem(
    val productId: ProductId,
    val confirmedQuantity: Quantity,
    val pendingQuantity: Quantity,
) {
    companion object {
        fun default(productId: ProductId) = InventoryItem(
            productId = productId,
            confirmedQuantity = Quantity.ZERO,
            pendingQuantity = Quantity.ZERO,
        )
    }
}

sealed interface InventoryValidationResult {
    object Valid : InventoryValidationResult
    object Invalid : InventoryValidationResult
}