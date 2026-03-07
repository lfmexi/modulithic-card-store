package io.lfmexi.cardshop.order.application.impl

import io.lfmexi.cardshop.order.application.OrderProductRegistrator
import io.lfmexi.cardshop.order.application.ports.OrderProductWriteRepository
import io.lfmexi.cardshop.order.domain.OrderProduct
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderProductRegistratorImpl(
    private val orderProductWriteRepository: OrderProductWriteRepository,
) : OrderProductRegistrator {
    @Transactional
    override fun register(product: OrderProduct): OrderProduct {
        return orderProductWriteRepository.save(product)
    }
}