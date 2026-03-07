package io.lfmexi.cardshop.inventory.application

import io.lfmexi.cardshop.inventory.domain.InventoryReservationResult
import io.lfmexi.cardshop.inventory.domain.ReserveInventoryCommand

interface InventoryReserver {
    fun reserveInventory(command: ReserveInventoryCommand): InventoryReservationResult
}