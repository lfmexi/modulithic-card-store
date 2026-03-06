package io.lfmexi.cardshop.catalog.application.impl

import io.lfmexi.cardshop.catalog.application.ProductCreationService
import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.catalog.domain.ProductCreationCommand
import io.lfmexi.cardshop.catalog.domain.event.ProductCreated
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductCreationServiceImpl(
    private val applicationEventPublisher: ApplicationEventPublisher,
) : ProductCreationService {
    @Transactional
    override fun createProduct(command: ProductCreationCommand): Product {
        val product = Product.create(command)

        applicationEventPublisher.publishEvent(ProductCreated.from(product))

        return product
    }
}