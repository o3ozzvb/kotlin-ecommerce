package com.loopers.infrastructure

import com.loopers.domain.BrandEntity
import com.loopers.domain.InventoryEntity
import com.loopers.domain.ProductEntity
import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import com.loopers.domain.product.Product
import com.loopers.repository.BrandJpaRepository
import com.loopers.repository.InventoryJpaRepository
import org.springframework.stereotype.Component

@Component
class ProductMapper(
    private val brandJpaRepository: BrandJpaRepository,
    private val inventoryJpaRepository: InventoryJpaRepository,
) {

    fun toProduct(productEntity: ProductEntity): Product {
        val brandEntity = brandJpaRepository.findById(productEntity.brandId)
            .orElseThrow { IllegalStateException("Brand not found: ${productEntity.brandId}") }

        val inventoryEntity = inventoryJpaRepository.findById(productEntity.inventoryId)
            .orElseThrow { IllegalStateException("Inventory not found: ${productEntity.inventoryId}") }

        return Product(
            id = productEntity.id,
            name = productEntity.name,
            brand = brandEntity.toBrand(),
            inventory = inventoryEntity.toInventory(),
            price = productEntity.price,
        )
    }

    private fun BrandEntity.toBrand(): Brand = Brand(
        id = this.id,
        name = this.name,
        description = this.description,
    )

    private fun InventoryEntity.toInventory(): Inventory = Inventory(
        id = this.id,
        totalStock = this.totalStock,
        actualStock = this.actualStock,
        availableStock = this.availableStock,
    )
}
