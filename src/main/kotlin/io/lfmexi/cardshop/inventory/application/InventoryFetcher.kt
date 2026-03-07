package io.lfmexi.cardshop.inventory.application

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.domain.Inventory

interface InventoryFetcher {
    fun fetch(shopId: ShopId): Inventory
}