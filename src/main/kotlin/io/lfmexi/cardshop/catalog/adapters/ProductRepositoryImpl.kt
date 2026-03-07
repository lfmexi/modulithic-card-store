package io.lfmexi.cardshop.catalog.adapters

import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.catalog.domain.ProductCreationCommand
import io.lfmexi.cardshop.catalog.domain.ProductRepository
import io.lfmexi.cardshop.common.domain.ProductId
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl : ProductRepository {
    override fun save(command: ProductCreationCommand): Product {
        TODO("Not yet implemented")
    }

    override fun findById(id: ProductId): Product? {
        TODO("Not yet implemented")
    }
}