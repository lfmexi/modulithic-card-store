package io.lfmexi.cardshop.catalog.application.impl

import io.lfmexi.cardshop.catalog.application.ProductCreationService
import io.lfmexi.cardshop.catalog.domain.CardProduct
import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.catalog.domain.ProductCreationCommand
import io.lfmexi.cardshop.common.event.CardProductCreated
import io.lfmexi.cardshop.common.event.ProductCreated
import io.lfmexi.cardshop.common.event.ProductEvent
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

        applicationEventPublisher.publishEvent(product.createEvent())

        return product
    }

    private fun Product.createEvent(): ProductEvent {
        return when (this) {
            is CardProduct -> CardProductCreated(
                productId = id,
                shopId = shopId,
                name = name,
                state = state,
                createdAt = createdAt,
                gameName = gameName,
                publisherName = publisherName,
            )
        }
    }
}