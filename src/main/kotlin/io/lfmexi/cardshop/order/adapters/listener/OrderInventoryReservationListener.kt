package io.lfmexi.cardshop.order.adapters.listener

import io.lfmexi.cardshop.common.event.InventoryReservationCreated
import io.lfmexi.cardshop.common.event.InventoryReservationRejected
import io.lfmexi.cardshop.order.application.OrderRejecter
import io.lfmexi.cardshop.order.application.OrderRequester
import io.lfmexi.cardshop.order.domain.RejectOrderCommand
import io.lfmexi.cardshop.order.domain.RequestOrderCommand
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class OrderInventoryReservationListener(
    private val orderRejecter: OrderRejecter,
    private val orderRequester: OrderRequester
) {
    @ApplicationModuleListener
    fun onInventoryReservationRejectedEvent(event: InventoryReservationRejected) {
        orderRejecter.reject(RejectOrderCommand(
            orderId = event.orderId,
            shopId = event.shopId,
            productId = event.productId,
        ))
    }

    @ApplicationModuleListener
    fun onInventoryReservationCreated(event: InventoryReservationCreated) {
        orderRequester.request(RequestOrderCommand(orderId = event.orderId))
    }
}