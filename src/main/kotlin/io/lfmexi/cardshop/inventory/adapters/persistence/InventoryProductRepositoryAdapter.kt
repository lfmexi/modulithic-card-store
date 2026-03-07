package io.lfmexi.cardshop.inventory.adapters.persistence

import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.adapters.persistence.dao.InventoryProductEntity
import io.lfmexi.cardshop.inventory.adapters.persistence.dao.InventoryProductId
import io.lfmexi.cardshop.inventory.application.ports.InventoryProductQueryRepository
import io.lfmexi.cardshop.inventory.application.ports.InventoryProductWriteRepository
import io.lfmexi.cardshop.inventory.domain.InventoryProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
class InventoryProductRepositoryAdapter(
    private val internalRepository: InventoryProductJpaRepository
) : InventoryProductWriteRepository, InventoryProductQueryRepository {
    @Transactional(propagation = Propagation.REQUIRED)
    override fun save(inventoryProduct: InventoryProduct): InventoryProduct {
        return internalRepository.save(InventoryProductEntity.from(inventoryProduct))
            .toDomain()
    }

    override fun findByShopIdAndProductId(
        shopId: ShopId,
        productId: ProductId
    ): InventoryProduct? {
        return internalRepository.findByShopIdAndProductId(
            shopId = shopId.value,
            productId = productId.value
        )?.toDomain()
    }
}

interface InventoryProductJpaRepository : JpaRepository<InventoryProductEntity, InventoryProductId> {
    fun findByShopIdAndProductId(shopId: String, productId: String): InventoryProductEntity?
}