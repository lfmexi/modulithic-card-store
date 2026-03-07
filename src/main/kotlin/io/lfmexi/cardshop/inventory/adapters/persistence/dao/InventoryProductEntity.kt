package io.lfmexi.cardshop.inventory.adapters.persistence.dao

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ProductState
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.domain.InventoryProduct
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "inventory_product")
@IdClass(InventoryProductId::class)
data class InventoryProductEntity(
    @Id
    val shopId: String,
    @Id
    val productId: String,
    @Enumerated(EnumType.STRING)
    val state: ProductState,
    val updatedAt: Instant,
) {
    fun toDomain(): InventoryProduct {
        return InventoryProduct(
            shopId = ShopId(shopId),
            productId = ProductId(productId),
            state = state,
            updatedAt = updatedAt,
        )
    }

    companion object {
        fun from(product: InventoryProduct): InventoryProductEntity {
            return InventoryProductEntity(
                shopId = product.shopId.value,
                productId = product.productId.value,
                state = product.state,
                updatedAt = product.updatedAt,
            )
        }
    }
}

class InventoryProductId: Serializable {
    var shopId: String = ""
    var productId: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InventoryProductId

        if (shopId != other.shopId) return false
        if (productId != other.productId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shopId.hashCode()
        result = 31 * result + productId.hashCode()
        return result
    }
}