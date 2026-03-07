package io.lfmexi.cardshop.order.application

import io.lfmexi.cardshop.order.domain.Order
import io.lfmexi.cardshop.order.domain.RejectOrderCommand

interface OrderRejecter {
    fun reject(command: RejectOrderCommand): Order?
}