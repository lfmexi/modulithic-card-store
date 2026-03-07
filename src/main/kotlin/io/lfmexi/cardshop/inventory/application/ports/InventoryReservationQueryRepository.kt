package io.lfmexi.cardshop.inventory.application.ports

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.domain.InventoryReservation

interface InventoryReservationQueryRepository {
    fun findAllByShopId(shopId: ShopId): List<InventoryReservation>
}