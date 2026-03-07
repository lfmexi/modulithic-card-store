package io.lfmexi.cardshop.order.adapters.listener

import io.lfmexi.cardshop.common.event.InventoryReservationRejected
import io.lfmexi.cardshop.order.application.OrderRejecter
import io.lfmexi.cardshop.order.domain.RejectOrderCommand
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class OrderInventoryReservationListener(
    private val orderRejecter: OrderRejecter
) {
    @ApplicationModuleListener
    fun onInventoryReservationRejectedEvent(event: InventoryReservationRejected) {
        orderRejecter.reject(RejectOrderCommand(
            orderId = event.orderId,
            shopId = event.shopId,
            productId = event.productId,
        ))
    }
}