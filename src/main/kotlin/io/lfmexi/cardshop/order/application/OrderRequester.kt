package io.lfmexi.cardshop.order.application

import io.lfmexi.cardshop.order.domain.CreateOrderCommand
import io.lfmexi.cardshop.order.domain.OrderRequestResult

interface OrderRequester {
    fun request(command: CreateOrderCommand): OrderRequestResult
}
