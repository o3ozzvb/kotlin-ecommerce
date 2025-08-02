package com.loopers.domain.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun save(product: Product): Product

    fun findProducts(
        brandId: Long?,
        sortBy: String,
        pageable: Pageable,
    ): Page<Product>

    fun findProductsData(
        brandId: Long?,
        sortBy: String,
        pageable: Pageable,
    ): Page<ProductData>
}
