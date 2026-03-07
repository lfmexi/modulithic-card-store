package io.lfmexi.cardshop.order.domain

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ProductState
import io.lfmexi.cardshop.common.domain.ShopId
import java.time.Instant

data class OrderProduct(
    val productId: ProductId,
    val shopId: ShopId,
    val state: ProductState,
    val updatedAt: Instant
)