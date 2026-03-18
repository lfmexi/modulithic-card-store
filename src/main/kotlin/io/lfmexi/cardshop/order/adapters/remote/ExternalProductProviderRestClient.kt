package io.lfmexi.cardshop.order.adapters.remote

import io.lfmexi.cardshop.order.application.ports.ExternalProductProviderClient
import io.lfmexi.cardshop.order.domain.Order
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ExternalProductProviderRestClient : ExternalProductProviderClient {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun request(order: Order) {
        log.info("Order Requested externally $order")
    }
}