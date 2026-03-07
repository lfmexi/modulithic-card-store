package io.lfmexi.cardshop.catalog.domain

import io.lfmexi.cardshop.common.domain.Name
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ProductState
import io.lfmexi.cardshop.common.domain.ShopId
import java.time.Instant

sealed interface Product {
    val id: ProductId
    val shopId: ShopId
    val name: Name
    val description : String
    val createdAt: Instant
    val updatedAt: Instant
    val state: ProductState

    companion object {
        fun create(command: ProductCreationCommand): Product {
            return when (command) {
                is CardProductCreationCommand -> CardProduct(
                    id = ProductId.generate(),
                    name = command.name,
                    description = command.description,
                    gameName = command.gameName,
                    publisherName = command.publisher,
                    createdAt = Instant.now(),
                    updatedAt = Instant.now(),
                    state = ProductState.ACTIVE,
                    shopId = command.shopId,
                )
            }
        }
    }
}

data class CardProduct(
    override val id: ProductId,
    override val shopId: ShopId,
    override val name: Name,
    override val description: String,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override val state: ProductState,
    val publisherName: Name,
    val gameName: Name,
): Product
