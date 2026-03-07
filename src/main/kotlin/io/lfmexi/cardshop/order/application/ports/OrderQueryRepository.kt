package io.lfmexi.cardshop.order.application.ports

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.order.domain.Order

interface OrderQueryRepository {
    fun findById(orderId: OrderId): Order?

    fun findAllByShopId(shopId: ShopId): List<Order>
}