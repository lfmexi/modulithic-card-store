package io.lfmexi.cardshop.order.application.impl

import io.lfmexi.cardshop.order.application.OrderRequester
import io.lfmexi.cardshop.order.application.ports.OrderWriteRepository
import io.lfmexi.cardshop.order.domain.CreateOrderCommand
import io.lfmexi.cardshop.order.domain.Order
import io.lfmexi.cardshop.order.domain.OrderRequestResult
import io.lfmexi.cardshop.common.event.OrderEvent
import io.lfmexi.cardshop.common.event.OrderRequested
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderRequesterImpl(
    private val orderWriteRepository: OrderWriteRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
): OrderRequester {
    @Transactional
    override fun request(command: CreateOrderCommand): OrderRequestResult {
        val orderResult =
            Order.createOrder(command)

        return when (orderResult) {
            is OrderRequestResult.Failure -> orderResult
            is OrderRequestResult.Success -> {
                val storedOrder = orderWriteRepository.save(orderResult.order)
                applicationEventPublisher.publishEvent(storedOrder.createEvent())
                OrderRequestResult.Success(
                    storedOrder,
                )
            }
        }
    }

    private fun Order.createEvent() = OrderRequested(
        orderId = id,
        shopId = shopId,
        productId = productId,
        state = state.name,
        orderedQuantity = orderedQuantity,
        confirmedQuantity = confirmedQuantity,
        orderedPrice = orderedPrice,
        lastConfirmedPrice = lastConfirmedPrice,
        currency = currency.toString(),
        occurredAt = updatedAt
    )
}