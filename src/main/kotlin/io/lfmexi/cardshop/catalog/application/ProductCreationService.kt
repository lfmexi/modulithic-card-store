package io.lfmexi.cardshop.catalog.application

import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.catalog.domain.ProductCreationCommand

interface ProductCreationService {
    fun createProduct(command: ProductCreationCommand): Product
}