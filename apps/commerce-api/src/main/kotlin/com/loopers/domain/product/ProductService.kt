package com.loopers.domain.product

import com.loopers.domain.brand.BrandRepository
import com.loopers.domain.inventory.InventoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val inventoryRepository: InventoryRepository,
) {
    fun findProducts(brandId: Long?, sortBy: String, pageable: Pageable): Page<Product> {
        // Use the new findProductsData method to avoid AOP issues
        val productDataPage = productRepository.findProductsData(brandId, sortBy, pageable)

        // Convert ProductData to full Product objects with proper Brand and Inventory
        val products = productDataPage.content.map { productData ->
            val brand = brandRepository.findById(productData.brandId)
                ?: throw IllegalStateException("Brand not found: ${productData.brandId}")

            val inventory = inventoryRepository.findById(productData.inventoryId)
                ?: throw IllegalStateException("Inventory not found: ${productData.inventoryId}")

            Product(
                id = productData.id,
                name = productData.name,
                brand = brand,
                inventory = inventory,
                price = productData.price,
            )
        }

        return PageImpl(products, pageable, productDataPage.totalElements)
    }
}
