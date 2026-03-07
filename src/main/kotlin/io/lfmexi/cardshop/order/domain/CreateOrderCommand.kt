package io.lfmexi.cardshop.order.domain

import io.lfmexi.cardshop.common.domain.OrderId
import io.lfmexi.cardshop.common.domain.Price
import io.lfmexi.cardshop.common.domain.ProductId
import io.lfmexi.cardshop.common.domain.Quantity
import io.lfmexi.cardshop.common.domain.ShopId
import java.util.*

sealed interface CreateOrderCommand {
    val shopId: ShopId
    val productId: ProductId
    val orderedQuantity: Quantity
    val orderedPrice: Price
    val currency: Currency

    companion object {
        fun buyCommand(
            shopId: ShopId,
            productId: ProductId,
            orderedQuantity: Quantity,
            orderedPrice: Price,
            currency: Currency,
        ): CreateOrderCommand {
            return CreateBuyOrderCommand(
                shopId = shopId,
                productId = productId,
                orderedQuantity = orderedQuantity,
                orderedPrice = orderedPrice,
                currency = currency,
            )
        }

        fun sellCommand(
            shopId: ShopId,
            productId: ProductId,
            orderedQuantity: Quantity,
            orderedPrice: Price,
            currency: Currency,
        ): CreateOrderCommand {
            return CreateSellOrderCommand(
                shopId = shopId,
                productId = productId,
                orderedQuantity = orderedQuantity.negate(),
                orderedPrice = orderedPrice,
                currency = currency,
            )
        }
    }
}

data class CreateBuyOrderCommand(
    override val shopId: ShopId,
    override val productId: ProductId,
    override val orderedQuantity: Quantity,
    override val orderedPrice: Price,
    override val currency: Currency,
): CreateOrderCommand

data class CreateSellOrderCommand(
    override val shopId: ShopId,
    override val productId: ProductId,
    override val orderedQuantity: Quantity,
    override val orderedPrice: Price,
    override val currency: Currency,
): CreateOrderCommand

data class RejectOrderCommand(
    val orderId: OrderId,
    val productId: ProductId,
    val shopId: ShopId
)