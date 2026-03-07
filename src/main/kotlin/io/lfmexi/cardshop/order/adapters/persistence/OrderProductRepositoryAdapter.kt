package io.lfmexi.cardshop.order.adapters.persistence

import io.lfmexi.cardshop.order.adapters.persistence.dao.OrderProductEntity
import io.lfmexi.cardshop.order.adapters.persistence.dao.OrderProductId
import io.lfmexi.cardshop.order.application.ports.OrderProductWriteRepository
import io.lfmexi.cardshop.order.domain.OrderProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
class OrderProductRepositoryAdapter(
    private val internalRepository: OrderProductJpaRepository
) : OrderProductWriteRepository {
    @Transactional(propagation = Propagation.REQUIRED)
    override fun save(product: OrderProduct): OrderProduct {
        return internalRepository.save(OrderProductEntity.from(product))
            .toDomain()
    }
}

interface OrderProductJpaRepository : JpaRepository<OrderProductEntity, OrderProductId>