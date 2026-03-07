package io.lfmexi.cardshop.order.adapters.persistence.dao

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ProductState
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.order.domain.OrderProduct
import jakarta.persistence.*
import java.io.Serializable
import java.time.Instant

@Entity
@Table(name = "order_product")
@IdClass(OrderProductId::class)
data class OrderProductEntity(
    @Id
    val shopId: String,
    @Id
    val productId: String,
    @Enumerated(EnumType.STRING)
    val state: ProductState,
    val updatedAt: Instant,
) {
    fun toDomain(): OrderProduct {
        return OrderProduct(
            shopId = ShopId(shopId),
            productId = ProductId(productId),
            state = state,
            updatedAt = updatedAt,
        )
    }

    companion object {
        fun from(product: OrderProduct): OrderProductEntity {
            return OrderProductEntity(
                shopId = product.shopId.value,
                productId = product.productId.value,
                state = product.state,
                updatedAt = product.updatedAt,
            )
        }
    }
}

class OrderProductId: Serializable {
    var shopId: String = ""
    var productId: String = ""
}