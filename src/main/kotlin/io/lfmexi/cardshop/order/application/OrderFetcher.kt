package io.lfmexi.cardshop.order.application

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.order.domain.Order

interface OrderFetcher {
    fun fetchOrders(shopId: ShopId): List<Order>
}