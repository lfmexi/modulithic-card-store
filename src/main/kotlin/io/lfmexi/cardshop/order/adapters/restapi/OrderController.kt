package io.lfmexi.cardshop.order.adapters.restapi

import io.lfmexi.cardshop.common.domain.Price
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.order.application.OrderFetcher
import io.lfmexi.cardshop.order.application.OrderCreator
import io.lfmexi.cardshop.order.domain.CreateOrderCommand
import io.lfmexi.cardshop.order.domain.Order
import io.lfmexi.cardshop.order.domain.OrderCreationResult
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/v1/shops/{shopId}/orders")
class OrderController(
    private val orderCreator: OrderCreator,
    private val orderFetcher: OrderFetcher,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(
        @PathVariable shopId: String,
        @RequestBody request: CreateOrderRequest
    ): Order {
        val orderResult = orderCreator.create(
            command = when (request.side) {
                OrderSide.BUY -> CreateOrderCommand.buyCommand(
                    shopId = ShopId(shopId),
                    productId = ProductId(request.productId),
                    orderedPrice = Price(request.price),
                    currency = request.currency,
                    orderedQuantity = Quantity(request.quantity)
                )
                OrderSide.SELL -> CreateOrderCommand.sellCommand(
                    shopId = ShopId(shopId),
                    productId = ProductId(request.productId),
                    orderedPrice = Price(request.price),
                    currency = request.currency,
                    orderedQuantity = Quantity(request.quantity)
                )
            }
        )

        return when (orderResult) {
            is OrderCreationResult.Success -> orderResult.order
            else -> throw IllegalArgumentException("Order request failed")
        }
    }

    @GetMapping
    fun retrieveOrders(
        @PathVariable shopId: String,
    ): List<Order> {
        return orderFetcher.fetchOrders(shopId = ShopId(shopId))
    }
}

data class CreateOrderRequest(
    val productId: String,
    val quantity: Long,
    val currency: Currency,
    val price: BigDecimal,
    val side: OrderSide
)

enum class OrderSide {
    BUY,
    SELL
}