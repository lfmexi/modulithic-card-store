package io.lfmexi.cardshop.order.application

import io.lfmexi.cardshop.order.domain.OrderRequestResult
import io.lfmexi.cardshop.order.domain.RequestOrderCommand

interface OrderRequester {
    fun request(command: RequestOrderCommand): OrderRequestResult
}