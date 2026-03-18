package io.lfmexi.cardshop.order.application

import io.lfmexi.cardshop.order.domain.CreateOrderCommand
import io.lfmexi.cardshop.order.domain.OrderCreationResult

interface OrderCreator {
    fun create(command: CreateOrderCommand): OrderCreationResult
}
