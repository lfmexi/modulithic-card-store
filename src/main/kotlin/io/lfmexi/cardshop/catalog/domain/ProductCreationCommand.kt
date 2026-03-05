package io.lfmexi.cardshop.catalog.domain

import io.lfmexi.cardshop.common.domain.Name

sealed interface ProductCreationCommand {
    val name: Name
    val description: String

    companion object {
        fun createCardCommand(
            name: String,
            description: String,
            gameName: String,
            publisher: String,
        ): ProductCreationCommand {
            return CardProductCreationCommand(
                name = Name(name),
                description = description,
                gameName = Name(name),
                publisher = Name(name),
            )
        }
    }
}

data class CardProductCreationCommand(
    override val name: Name,
    override val description: String,
    val gameName: Name,
    val publisher: Name,
): ProductCreationCommand