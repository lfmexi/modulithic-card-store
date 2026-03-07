package io.lfmexi.cardshop.order.adapters.persistence

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.order.adapters.persistence.dao.OrderEntity
import io.lfmexi.cardshop.order.application.ports.OrderQueryRepository
import io.lfmexi.cardshop.order.application.ports.OrderWriteRepository
import io.lfmexi.cardshop.order.domain.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderRepositoryAdapter(
    private val orderJpaRepository: OrderJpaRepository
) : OrderWriteRepository, OrderQueryRepository {
    @Transactional
    override fun save(order: Order): Order {
        return orderJpaRepository.save(OrderEntity.from(order))
            .toDomain()
    }

    override fun findById(orderId: OrderId): Order? {
        return orderJpaRepository.findById(orderId.value)
            .takeIf { it.isPresent }
            ?.get()
            ?.toDomain()
    }

    override fun findAllByShopId(shopId: ShopId): List<Order> {
        return orderJpaRepository.findAllByShopId(shopId.value)
            .map { it.toDomain() }
    }
}

interface OrderJpaRepository : JpaRepository<OrderEntity, String> {
    fun findAllByShopId(shopId: String): List<OrderEntity>
}