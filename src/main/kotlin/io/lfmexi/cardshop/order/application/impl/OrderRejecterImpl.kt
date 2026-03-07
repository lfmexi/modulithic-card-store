package io.lfmexi.cardshop.order.application.impl

import io.lfmexi.cardshop.order.application.OrderRejecter
import io.lfmexi.cardshop.order.application.ports.OrderQueryRepository
import io.lfmexi.cardshop.order.application.ports.OrderWriteRepository
import io.lfmexi.cardshop.order.domain.Order
import io.lfmexi.cardshop.order.domain.RejectOrderCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderRejecterImpl(
    private val orderQueryRepository: OrderQueryRepository,
    private val orderWriteRepository: OrderWriteRepository
) : OrderRejecter {
    @Transactional
    override fun reject(command: RejectOrderCommand): Order? {
        val order = orderQueryRepository.findById(command.orderId)
            ?: return null

        val rejectedOrder = order.reject()
            ?: return null

        return orderWriteRepository.save(rejectedOrder)
    }
}