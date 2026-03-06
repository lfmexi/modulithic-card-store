package io.lfmexi.cardshop.inventory.messaging

import io.lfmexi.cardshop.catalog.domain.event.ProductCreated
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
class InventoryProductCreatedListener(
    private val objectMapper: ObjectMapper
) {
    @ApplicationModuleListener
    fun onProductCreated(event: ProductCreated) {
        println(objectMapper.writeValueAsString(event))
    }
}