package io.lfmexi.cardshop.inventory.application.ports

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.domain.InventoryProduct

interface InventoryProductQueryRepository {
    fun findByShopIdAndProductId(
        shopId: ShopId,
        productId: ProductId
    ): InventoryProduct?
}