package com.loopers.domain.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {
    fun findProductsData(brandId: Long?, sortBy: String, pageable: Pageable): Page<ProductData> {
        return productRepository.findProductsData(brandId, sortBy, pageable)
    }

    fun findById(id: Long): ProductData? {
        return productRepository.findById(id)
    }

    fun save(productData: ProductData): ProductData {
        return productRepository.save(productData)
    }

    fun deleteById(id: Long) {
        productRepository.deleteById(id)
    }
}
