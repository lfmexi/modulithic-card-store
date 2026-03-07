package io.lfmexi.cardshop.order.application.ports

import io.lfmexi.cardshop.order.domain.OrderProduct

interface OrderProductWriteRepository {
    fun save(product: OrderProduct): OrderProduct
}