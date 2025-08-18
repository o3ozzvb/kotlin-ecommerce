package com.loopers.domain.product

import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import java.math.BigDecimal

data class Product(
    val id: Long,
    val name: String,
    val brand: Brand,
    val inventory: Inventory,
    val price: BigDecimal,
    val likeCount: Long = 0L,
) {
    fun consumeStock(quantity: Int): Product {
        val newInventory = inventory.consume(quantity)
        return copy(inventory = newInventory)
    }

    fun addStock(quantity: Int): Product {
        val newInventory = inventory.add(quantity)
        return copy(inventory = newInventory)
    }

    fun isAvailable(): Boolean = inventory.isAvailable()

    fun updatePrice(newPrice: BigDecimal): Product {
        return copy(price = newPrice)
    }
}
