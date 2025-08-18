package com.loopers.domain.product

import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import java.math.BigDecimal

data class ProductData(
    val id: Long,
    val name: String,
    val brandId: Long,
    val inventoryId: Long,
    val price: BigDecimal,
    val likeCount: Long = 0L,
) {
    fun toProduct(brand: Brand, inventory: Inventory): Product {
        return Product(
            id = this.id,
            name = this.name,
            brand = brand,
            inventory = inventory,
            price = this.price,
            likeCount = this.likeCount,
        )
    }
}
