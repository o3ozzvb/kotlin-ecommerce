package com.loopers.infrastructure.product

import com.loopers.domain.ProductEntity
import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductRepository
import com.loopers.repository.BrandJpaRepository
import com.loopers.repository.InventoryJpaRepository
import com.loopers.repository.ProductJpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val brandJpaRepository: BrandJpaRepository,
    private val inventoryJpaRepository: InventoryJpaRepository,
) : ProductRepository {

    override fun find(id: Long): Product? {
        return productJpaRepository.findById(id)
            .map { productEntity ->
                convertToProduct(productEntity)
            }
            .orElse(null)
    }

    override fun findAll(): List<Product> {
        return productJpaRepository.findAll()
            .map { convertToProduct(it) }
    }

    override fun findAll(pageable: Pageable): Page<Product> {
        val page = productJpaRepository.findAll(pageable)
        val products = page.content.map { convertToProduct(it) }
        return PageImpl(products, pageable, page.totalElements)
    }

    private fun convertToProduct(productEntity: ProductEntity): Product {
        val brandEntity = brandJpaRepository.findById(productEntity.brandId)
            .orElseThrow { IllegalStateException("Brand not found: ${productEntity.brandId}") }

        val inventoryEntity = inventoryJpaRepository.findById(productEntity.inventoryId)
            .orElseThrow { IllegalStateException("Inventory Not found: ${productEntity.inventoryId}") }

        val brand = Brand(
            id = brandEntity.id,
            name = productEntity.name,
            description = brandEntity.description,
        )

        val inventory = Inventory(
            id = productEntity.inventoryId,
            totalStock = inventoryEntity.totalStock,
            actualStock = inventoryEntity.actualStock,
            availableStock = inventoryEntity.availableStock,
        )

        return Product(
            id = productEntity.id,
            name = productEntity.name,
            brand = brand,
            inventory = inventory,
            price = productEntity.price,
        )
    }
}
