package io.lfmexi.cardshop.common.event

import io.lfmexi.cardshop.common.domain.Name
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ProductState
import io.lfmexi.cardshop.common.domain.ShopId
import org.jmolecules.event.types.DomainEvent
import java.time.Instant

sealed interface ProductEvent : DomainEvent {
    val productId: ProductId
    val name: Name
    val shopId: ShopId
    val state: ProductState
}

sealed interface ProductCreated : ProductEvent {
    val createdAt: Instant
}

data class CardProductCreated(
    override val productId: ProductId,
    override val name: Name,
    override val shopId: ShopId,
    override val state: ProductState,
    override val createdAt: Instant,
    val gameName: Name,
    val publisherName: Name,
): ProductCreated