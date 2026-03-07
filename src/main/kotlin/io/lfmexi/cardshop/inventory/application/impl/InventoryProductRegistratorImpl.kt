package io.lfmexi.cardshop.inventory.application.impl

import io.lfmexi.cardshop.inventory.application.InventoryProductRegistrator
import io.lfmexi.cardshop.inventory.application.ports.InventoryProductWriteRepository
import io.lfmexi.cardshop.inventory.domain.InventoryProduct
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryProductRegistratorImpl(
    private val inventoryProductWriteRepository: InventoryProductWriteRepository,
) : InventoryProductRegistrator {
    @Transactional
    override fun register(product: InventoryProduct): InventoryProduct {
        return inventoryProductWriteRepository.save(product)
    }
}