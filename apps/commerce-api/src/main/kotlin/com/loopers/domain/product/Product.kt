package com.loopers.domain.product

import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import java.math.BigDecimal

data class Product(
    val id: Long,
    private var name: String,
    val brand: Brand,
    private val inventory: Inventory,
    private var price: BigDecimal,
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

    fun updatePrice(price: BigDecimal) {
        this.price = price
    }

}
