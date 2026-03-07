package io.lfmexi.cardshop.inventory.application.ports

import io.lfmexi.cardshop.inventory.domain.InventoryReservation

interface InventoryReservationWriteRepository {
    fun save(reservation: InventoryReservation): InventoryReservation
}