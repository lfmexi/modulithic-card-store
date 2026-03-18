package io.lfmexi.cardshop.order.application.impl

import io.lfmexi.cardshop.order.application.OrderCreator
import io.lfmexi.cardshop.order.application.ports.OrderWriteRepository
import io.lfmexi.cardshop.order.domain.CreateOrderCommand
import io.lfmexi.cardshop.order.domain.Order
import io.lfmexi.cardshop.order.domain.OrderCreationResult
import io.lfmexi.cardshop.common.event.OrderCreated
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderCreatorImpl(
    private val orderWriteRepository: OrderWriteRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
): OrderCreator {
    @Transactional
    override fun create(command: CreateOrderCommand): OrderCreationResult {
        val orderResult =
            Order.createOrder(command)

        return when (orderResult) {
            is OrderCreationResult.Failure -> orderResult
            is OrderCreationResult.Success -> {
                val storedOrder = orderWriteRepository.save(orderResult.order)
                applicationEventPublisher.publishEvent(storedOrder.createEvent())
                OrderCreationResult.Success(
                    storedOrder,
                )
            }
        }
    }

    private fun Order.createEvent() = OrderCreated(
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