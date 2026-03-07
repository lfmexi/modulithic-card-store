package io.lfmexi.cardshop.inventory.adapters.restapi

import io.lfmexi.cardshop.common.domain.ShopId
import io.lfmexi.cardshop.inventory.application.InventoryFetcher
import io.lfmexi.cardshop.inventory.domain.Inventory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/shops/{shopId}/inventory")
class InventoryController(
    private val inventoryFetcher: InventoryFetcher
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun inventory(@PathVariable("shopId") shopId: String): Inventory {
        return inventoryFetcher.fetch(ShopId(shopId))
    }
}