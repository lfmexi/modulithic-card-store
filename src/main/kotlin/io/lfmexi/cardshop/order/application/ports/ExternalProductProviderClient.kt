package io.lfmexi.cardshop.order.application.ports

import io.lfmexi.cardshop.order.domain.Order

interface ExternalProductProviderClient {
    fun request(order: Order)
}