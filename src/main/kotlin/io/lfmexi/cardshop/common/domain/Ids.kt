package io.lfmexi.cardshop.common.domain

import java.util.UUID

@JvmInline
value class ProductId(val value: String) {
    companion object {
        fun generate() = ProductId(UUID.randomUUID().toString())
    }
}

@JvmInline
value class ShopId(val value: String) {
    companion object {
        fun generate() = ShopId(UUID.randomUUID().toString())
    }
}

@JvmInline
value class OrderId(val value: String) {
    companion object {
        fun generate() = OrderId(UUID.randomUUID().toString())
    }
}