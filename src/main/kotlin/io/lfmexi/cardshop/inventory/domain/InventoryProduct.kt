package io.lfmexi.cardshop.inventory.domain

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ProductState
import io.lfmexi.cardshop.common.domain.ShopId
import java.time.Instant

data class InventoryProduct(
    val shopId: ShopId,
    val productId: ProductId,
    val state: ProductState,
    val updatedAt: Instant
)