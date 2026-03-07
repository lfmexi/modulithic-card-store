package io.lfmexi.cardshop.catalog.domain

import io.lfmexi.cardshop.common.domain.ProductId

interface ProductRepository {
    fun save(command: ProductCreationCommand): Product

    fun findById(id: ProductId): Product?
}