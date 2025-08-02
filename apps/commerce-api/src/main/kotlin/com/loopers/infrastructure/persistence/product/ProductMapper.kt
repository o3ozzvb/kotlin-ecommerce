package com.loopers.infrastructure.persistence.product

import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import com.loopers.domain.product.Product
import com.loopers.infrastructure.persistence.brand.BrandJpaRepository
import com.loopers.infrastructure.persistence.brand.BrandEntity
import com.loopers.infrastructure.persistence.inventory.InventoryEntity
import com.loopers.infrastructure.persistence.inventory.InventoryJpaRepository
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
