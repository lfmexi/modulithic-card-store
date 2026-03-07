package io.lfmexi.cardshop.inventory.application.ports

import io.lfmexi.cardshop.inventory.domain.InventoryProduct

interface InventoryProductWriteRepository {
    fun save(inventoryProduct: InventoryProduct): InventoryProduct
}