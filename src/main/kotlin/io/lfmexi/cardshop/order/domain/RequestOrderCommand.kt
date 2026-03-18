package io.lfmexi.cardshop.order.domain

import io.lfmexi.cardshop.common.domain.OrderId

data class RequestOrderCommand(
    val orderId: OrderId,
)