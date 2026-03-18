package io.lfmexi.cardshop.order.application.impl

import io.lfmexi.cardshop.order.application.OrderRequester
import io.lfmexi.cardshop.order.application.ports.ExternalProductProviderClient
import io.lfmexi.cardshop.order.application.ports.OrderQueryRepository
import io.lfmexi.cardshop.order.application.ports.OrderWriteRepository
import io.lfmexi.cardshop.order.domain.OrderRequestResult
import io.lfmexi.cardshop.order.domain.RequestOrderCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class OrderRequesterImpl(
    private val orderQueryRepository: OrderQueryRepository,
    private val orderWriteRepository: OrderWriteRepository,
    private val externalProductProviderClient: ExternalProductProviderClient

): OrderRequester {
    @Transactional
    override fun request(command: RequestOrderCommand): OrderRequestResult {
        val order = orderQueryRepository.findById(command.orderId)
            ?: return OrderRequestResult.Failure

        val requestedOrder = order.request()
            ?: return OrderRequestResult.Failure

        orderWriteRepository.save(requestedOrder)
        externalProductProviderClient.request(requestedOrder)

        return OrderRequestResult.Success(requestedOrder)
    }
}