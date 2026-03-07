package io.lfmexi.cardshop.order.application

import io.lfmexi.cardshop.order.domain.OrderProduct

interface OrderProductRegistrator {
    fun register(product: OrderProduct): OrderProduct
}