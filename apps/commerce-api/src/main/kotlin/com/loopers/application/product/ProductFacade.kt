package com.loopers.application.product

import com.loopers.domain.brand.BrandService
import com.loopers.domain.inventory.InventoryService
import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductFacade(
    private val productService: ProductService,
    private val brandService: BrandService,
    private val inventoryService: InventoryService,
) {
    fun findProducts(brandId: Long?, sortBy: String, pageable: Pageable): Page<Product> {
        val productDataPage = productService.findProductsData(brandId, sortBy, pageable)

        val products = productDataPage.content.map { productData ->
            val brand = brandService.findById(productData.brandId)
            val inventory = inventoryService.findById(productData.inventoryId)

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
