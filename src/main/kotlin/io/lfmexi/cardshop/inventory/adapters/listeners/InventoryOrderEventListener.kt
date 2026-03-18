package io.lfmexi.cardshop.inventory.adapters.listeners

import io.lfmexi.cardshop.inventory.application.InventoryReserver
import io.lfmexi.cardshop.inventory.domain.ReserveInventoryCommand
import io.lfmexi.cardshop.common.event.OrderCreated
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
    fun onOrderCreated(
        orderCreated: OrderCreated
    ) {
        logger.info("Order requested: $orderCreated")
        val command = ReserveInventoryCommand(
            shopId = orderCreated.shopId,
            orderId = orderCreated.orderId,
            productId = orderCreated.productId,
            orderedQuantity = orderCreated.orderedQuantity
        )
        val result = inventoryReserver.reserveInventory(command)

        if (result is InventoryReservationResult.Success) {
            logger.info("Inventory reserved for order ${orderCreated.orderId}")
        } else {
            logger.warn("Inventory reserve failed for order ${orderCreated.orderId}, reason $result")
        }
    }
}