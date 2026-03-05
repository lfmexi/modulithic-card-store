package io.lfmexi.cardshop.catalog.domain

import io.lfmexi.cardshop.common.domain.Id

interface ProductRepository {
    fun save(command: ProductCreationCommand): Product

    fun findById(id: Id): Product?
}