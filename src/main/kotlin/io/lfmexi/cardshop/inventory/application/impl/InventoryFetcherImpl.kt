package io.lfmexi.cardshop.inventory.application.impl

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.application.InventoryFetcher
import io.lfmexi.cardshop.inventory.application.ports.InventoryReservationQueryRepository
import io.lfmexi.cardshop.inventory.domain.Inventory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryFetcherImpl(
    private val inventoryReservationQueryRepository: InventoryReservationQueryRepository
) : InventoryFetcher {
    @Transactional
    override fun fetch(shopId: ShopId): Inventory {
        return Inventory.load(
            shopId = shopId,
            reservations = inventoryReservationQueryRepository.findAllByShopId(shopId)
        )
    }
}