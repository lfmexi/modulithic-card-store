package io.lfmexi.cardshop.order.application.impl

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.order.application.OrderFetcher
import io.lfmexi.cardshop.order.application.ports.OrderQueryRepository
import io.lfmexi.cardshop.order.domain.Order
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderFetcherImpl(
    private val orderQueryRepository: OrderQueryRepository
): OrderFetcher {
    @Transactional(readOnly = true)
    override fun fetchOrders(shopId: ShopId): List<Order> {
        return orderQueryRepository.findAllByShopId(shopId)
    }
}