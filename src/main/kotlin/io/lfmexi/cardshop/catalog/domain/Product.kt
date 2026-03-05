package io.lfmexi.cardshop.catalog.domain

import io.lfmexi.cardshop.common.domain.Id
import io.lfmexi.cardshop.common.domain.Name

sealed interface Product {
    val id : Id
    val name: Name
    val description : String

    companion object {
        fun create(command: ProductCreationCommand): Product {
            return when (command) {
                is CardProductCreationCommand -> CardProduct(
                    id = Id.generate(),
                    name = command.name,
                    description = command.description,
                    gameName = command.gameName,
                    publisherName = command.publisher,
                )
            }
        }
    }
}

data class CardProduct(
    override val id: Id,
    override val name: Name,
    override val description: String,
    val publisherName: Name,
    val gameName: Name,
): Product
