package io.lfmexi.cardshop.inventory.adapters.listeners

import io.lfmexi.cardshop.inventory.application.InventoryReserver
import io.lfmexi.cardshop.inventory.domain.ReserveInventoryCommand
import io.lfmexi.cardshop.common.event.OrderRequested
import io.lfmexi.cardshop.inventory.domain.InventoryReservationResult
import org.slf4j.LoggerFactory
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class InventoryOrderEventListener(
    private val inventoryReserver: InventoryReserver
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ApplicationModuleListener
    fun onOrderRequested(
        orderRequested: OrderRequested
    ) {
        logger.info("Order requested: $orderRequested")
        val command = ReserveInventoryCommand(
            shopId = orderRequested.shopId,
            orderId = orderRequested.orderId,
            productId = orderRequested.productId,
            orderedQuantity = orderRequested.orderedQuantity
        )
        val result = inventoryReserver.reserveInventory(command)

        if (result is InventoryReservationResult.Success) {
            logger.info("Inventory reserved for order ${orderRequested.orderId}")
        } else {
            logger.warn("Inventory reserve failed for order ${orderRequested.orderId}, reason $result")
        }
    }
}