package io.lfmexi.cardshop.catalog.api

import io.lfmexi.cardshop.catalog.application.ProductCreationService
import io.lfmexi.cardshop.catalog.domain.Product
import io.lfmexi.cardshop.catalog.domain.ProductCreationCommand
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/catalog/products")
class ProductController(
    private val productCreationService: ProductCreationService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody request: ProductRequest): Product {
        return productCreationService.createProduct(request.toCommand())
    }
}

data class ProductRequest(
    val name: String,
    val description: String,
    val type: SupportedProductType,
    val cardProductRequest: CardProductCreationRequest? = null
) {
    fun toCommand(): ProductCreationCommand {
        return when (type) {
            SupportedProductType.CARD ->  {
                requireNotNull(cardProductRequest) { "Product cannot have a null cardProductRequest" }
                ProductCreationCommand
                    .createCardCommand(
                        name = name,
                        description = description,
                        publisher = cardProductRequest.publisherName,
                        gameName = cardProductRequest.gameName,
                    )
            }
        }
    }
}

data class CardProductCreationRequest(
    val publisherName: String,
    val gameName: String,
)

enum class SupportedProductType {
    CARD;
}