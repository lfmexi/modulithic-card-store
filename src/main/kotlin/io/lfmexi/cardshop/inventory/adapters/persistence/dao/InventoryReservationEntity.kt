package io.lfmexi.cardshop.inventory.adapters.persistence.dao

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.domain.InventoryReservation
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import java.io.Serializable
import java.time.Instant

@Entity
@IdClass(InventoryReservationId::class)
@Table(name = "inventory_reservation")
data class InventoryReservationEntity(
    @Id
    val shopId: String,
    @Id
    val productId: String,
    @Id
    val orderId: String,
    val orderedQuantity: Long,
    val confirmedQuantity: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    fun toDomain(): InventoryReservation {
        return InventoryReservation(
            shopId = ShopId(shopId),
            productId = ProductId(productId),
            orderId = OrderId(orderId),
            orderedQuantity = Quantity(orderedQuantity),
            confirmedQuantity = Quantity(confirmedQuantity),
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }

    companion object {
        fun from(reservation: InventoryReservation): InventoryReservationEntity {
            return InventoryReservationEntity(
                shopId = reservation.shopId.value,
                productId = reservation.productId.value,
                orderId = reservation.orderId.value,
                orderedQuantity = reservation.orderedQuantity.value,
                confirmedQuantity = reservation.confirmedQuantity.value,
                createdAt = reservation.createdAt,
                updatedAt = reservation.updatedAt,
            )
        }
    }
}

class InventoryReservationId: Serializable {
    var shopId: String = ""
    var productId: String = ""
    var orderId: String = ""
}