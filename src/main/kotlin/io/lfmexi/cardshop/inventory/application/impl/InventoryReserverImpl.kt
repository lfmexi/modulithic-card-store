package io.lfmexi.cardshop.inventory.application.impl

import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.event.InventoryReservationCreated
import io.lfmexi.cardshop.common.event.InventoryReservationRejected
import io.lfmexi.cardshop.inventory.application.InventoryFetcher
import io.lfmexi.cardshop.inventory.application.InventoryReserver
import io.lfmexi.cardshop.inventory.application.ports.InventoryProductQueryRepository
import io.lfmexi.cardshop.inventory.application.ports.InventoryReservationWriteRepository
import io.lfmexi.cardshop.inventory.domain.InventoryReservation
import io.lfmexi.cardshop.inventory.domain.InventoryReservationResult
import io.lfmexi.cardshop.inventory.domain.InventoryValidationResult
import io.lfmexi.cardshop.inventory.domain.ReserveInventoryCommand
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class InventoryReserverImpl(
    private val inventoryProductQueryRepository: InventoryProductQueryRepository,
    private val inventoryFetcher: InventoryFetcher,
    private val inventoryReservationWriteRepository: InventoryReservationWriteRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : InventoryReserver {
    @Transactional
    override fun reserveInventory(command: ReserveInventoryCommand): InventoryReservationResult {
        inventoryProductQueryRepository.findByShopIdAndProductId(command.shopId, command.productId)
            ?: return InventoryReservationResult.Failure("Active Product not found").apply {
                applicationEventPublisher.publishEvent(command.rejectedEvent(this.reason))
            }

        val reservation = InventoryReservation.create(command)

        val validationResult = inventoryFetcher.fetch(command.shopId)
            .validate(reservation)

        return when (validationResult) {
            is InventoryValidationResult.Valid -> {
                inventoryReservationWriteRepository.save(reservation)
                applicationEventPublisher.publishEvent(reservation.createdEvent())
                InventoryReservationResult.Success(reservation)
            }
            else -> InventoryReservationResult.Failure("Could not reserve inventory").apply {
                applicationEventPublisher.publishEvent(command.rejectedEvent(this.reason))
            }
        }
    }

    private fun InventoryReservation.createdEvent() = InventoryReservationCreated(
        shopId = shopId,
        orderId = orderId,
        productId = productId,
        orderedQuantity = orderedQuantity,
        confirmedQuantity = confirmedQuantity,
        occurredAt = updatedAt,
    )

    private fun ReserveInventoryCommand.rejectedEvent(
        reason: String
    ) = InventoryReservationRejected(
        shopId = shopId,
        orderId = orderId,
        productId = productId,
        orderedQuantity = orderedQuantity,
        confirmedQuantity = Quantity.ZERO,
        occurredAt = Instant.now(),
        reason = reason
    )
}