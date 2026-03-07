package io.lfmexi.cardshop.order.application.ports

import io.lfmexi.cardshop.order.domain.Order

interface OrderWriteRepository {
    fun save(order: Order): Order
}