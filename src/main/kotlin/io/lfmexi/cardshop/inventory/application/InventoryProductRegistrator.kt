package io.lfmexi.cardshop.inventory.application

import io.lfmexi.cardshop.inventory.domain.InventoryProduct

interface InventoryProductRegistrator {
    fun register(product: InventoryProduct): InventoryProduct
}