package io.lfmexi.cardshop.order.adapters.listener

import io.lfmexi.cardshop.common.event.ProductCreated
import io.lfmexi.cardshop.order.application.OrderProductRegistrator
import io.lfmexi.cardshop.order.domain.OrderProduct
import org.slf4j.LoggerFactory
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class OrderProductCreatedListener(
    private val orderProductRegistrator: OrderProductRegistrator,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ApplicationModuleListener
    fun onProductCreated(event: ProductCreated) {
        logger.info("Received a new product created event for product ${event.productId} and shop ${event.shopId}")

        orderProductRegistrator.register(
            product = OrderProduct(
                shopId = event.shopId,
                productId = event.productId,
                state = event.state,
                updatedAt = event.createdAt
            )
        )

        logger.info("Created product ${event.productId} and shop ${event.shopId} for orders")
    }
}