package io.lfmexi.cardshop.catalog.domain.event

import io.lfmexi.cardshop.catalog.domain.CardProduct
import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.common.domain.Id
import io.lfmexi.cardshop.common.domain.Name
import io.lfmexi.cardshop.common.domain.ProductState
import org.jmolecules.event.types.DomainEvent
import java.time.Instant

sealed interface ProductEvent : DomainEvent {
    val id: Id
    val name: Name
    val state: ProductState
}

sealed interface ProductCreated : ProductEvent {
    val createdAt: Instant

    companion object {
        fun from(product: Product): ProductCreated {
            return when (product) {
                is CardProduct -> CardProductCreated(
                    id = product.id,
                    name = product.name,
                    state = product.state,
                    createdAt = product.createdAt,
                    gameName = product.gameName,
                    publisherName = product.publisherName,
                )
            }
        }
    }
}

data class CardProductCreated(
    override val id: Id,
    override val name: Name,
    override val state: ProductState,
    override val createdAt: Instant,
    val gameName: Name,
    val publisherName: Name,
): ProductCreated