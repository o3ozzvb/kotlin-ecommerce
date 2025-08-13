package com.loopers.domain.product

import org.springframework.data.domain.Page

interface ProductCacheRepository {
    fun getProductList(cacheKey: ProductListCacheKey): Page<ProductData>?
    fun setProductList(cacheKey: ProductListCacheKey, data: Page<ProductData>)
    fun getProductDetail(productId: Long): Product?
    fun setProductDetail(product: Product)
//    fun evictProductList(sortBy: String, page: Int? = null, categoryId: Long? = null, brandId: Long? = null)
//    fun evictProductDetail(productId: Long)
//    fun evictAllProductCaches(): Long
}
