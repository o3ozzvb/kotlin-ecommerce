package com.loopers.domain.product

interface ProductCacheRepository {
    fun getProductList(cacheKey: ProductListCacheKey): ProductListResponse?
    fun setProductList(cacheKey: ProductListCacheKey, data: ProductListResponse)
    fun getProductDetail(productId: Long): Product?
    fun setProductDetail(product: Product)
    fun evictProductList(sortBy: String, page: Int? = null, categoryId: Long? = null, brandId: Long? = null)
    fun evictProductDetail(productId: Long)
    fun evictAllProductCaches(): Long
    fun getCacheStats(): ProductCacheStats
}
