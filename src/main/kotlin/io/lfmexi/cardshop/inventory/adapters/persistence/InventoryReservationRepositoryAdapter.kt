package io.lfmexi.cardshop.inventory.adapters.persistence

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.adapters.persistence.dao.InventoryReservationEntity
import io.lfmexi.cardshop.inventory.adapters.persistence.dao.InventoryReservationId
import io.lfmexi.cardshop.inventory.application.ports.InventoryReservationQueryRepository
import io.lfmexi.cardshop.inventory.application.ports.InventoryReservationWriteRepository
import io.lfmexi.cardshop.inventory.domain.InventoryReservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
class InventoryReservationRepositoryAdapter(
    private val inventoryReservationJpaRepository: InventoryReservationJpaRepository,
) :
    InventoryReservationWriteRepository,
    InventoryReservationQueryRepository {

    @Transactional(propagation = Propagation.REQUIRED)
    override fun save(reservation: InventoryReservation): InventoryReservation {
        return inventoryReservationJpaRepository.save(
            InventoryReservationEntity.from(reservation)
        ).toDomain()
    }

    override fun findAllByShopId(shopId: ShopId): List<InventoryReservation> {
        return inventoryReservationJpaRepository.findAllByShopId(shopId.value)
            .map { it.toDomain() }
    }
}

interface InventoryReservationJpaRepository : JpaRepository<InventoryReservationEntity, InventoryReservationId> {
    fun findAllByShopId(shopId: String): List<InventoryReservationEntity>
}