package io.lfmexi.cardshop.inventory.adapters.listeners

import io.lfmexi.cardshop.common.event.ProductCreated
import io.lfmexi.cardshop.inventory.application.InventoryProductRegistrator
import io.lfmexi.cardshop.inventory.domain.InventoryProduct
import org.slf4j.LoggerFactory
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class InventoryProductCreatedListener(
    private val inventoryProductRegistrator: InventoryProductRegistrator
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ApplicationModuleListener
    fun onProductCreated(event: ProductCreated) {
        logger.info("Registering new inventory product with id ${event.productId} for the shop ${event.shopId}")

        inventoryProductRegistrator.register(
            product = InventoryProduct(
                productId = event.productId,
                shopId = event.shopId,
                state = event.state,
                updatedAt = event.createdAt,
            )
        )

        logger.info("Inventory product with id ${event.productId} for the shop ${event.shopId} registered successfully")
    }
}