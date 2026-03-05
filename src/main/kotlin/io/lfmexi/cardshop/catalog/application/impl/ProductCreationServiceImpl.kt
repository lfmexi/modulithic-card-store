package io.lfmexi.cardshop.catalog.application.impl

import io.lfmexi.cardshop.catalog.application.ProductCreationService
import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.catalog.domain.ProductCreationCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductCreationServiceImpl : ProductCreationService {
    @Transactional
    override fun createProduct(command: ProductCreationCommand): Product {
        return Product.create(command)
    }
}