package io.lfmexi.cardshop.catalog.domain

import io.lfmexi.cardshop.common.domain.Name
import io.lfmexi.cardshop.common.domain.ShopId

sealed interface ProductCreationCommand {
    val shopId: ShopId
    val name: Name
    val description: String

    companion object {
        fun createCardCommand(
            shopId: String,
            name: String,
            description: String,
            gameName: String,
            publisher: String,
        ): ProductCreationCommand {
            return CardProductCreationCommand(
                shopId = ShopId(shopId),
                name = Name(name),
                description = description,
                gameName = Name(name),
                publisher = Name(name),
            )
        }
    }
}

data class CardProductCreationCommand(
    override val shopId: ShopId,
    override val name: Name,
    override val description: String,
    val gameName: Name,
    val publisher: Name,
): ProductCreationCommand