package com.loopers.infrastructure.repository

import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductCacheRepository
import com.loopers.infrastructure.cache.CacheRepository
import com.loopers.infrastructure.cache.ProductListCacheKey
import com.loopers.interfaces.api.product.ProductListResponse
import org.springframework.stereotype.Repository

@Repository
class ProductCacheRepositoryImpl(
    private val cacheRepository: CacheRepository,
) : ProductCacheRepository {
    override fun getProductList(cacheKey: ProductListCacheKey): ProductListResponse {
        TODO("Not yet implemented")
    }

    override fun setProductList(cacheKey: ProductListCacheKey, data: ProductListResponse) {
        TODO("Not yet implemented")
    }


    override fun getProductDetail(productId: Long): Product? {
        TODO("Not yet implemented")
    }

    override fun setProductDetail(product: Product) {
        TODO("Not yet implemented")
    }

    override fun evictProductList(sortBy: String, page: Int?, categoryId: Long?, brandId: Long?) {
        TODO("Not yet implemented")
    }

    override fun evictProductDetail(productId: Long) {
        TODO("Not yet implemented")
    }

    override fun evictAllProductCaches(): Long {
        TODO("Not yet implemented")
    }

    override fun getCacheStats(): ProductCacheStats {
        TODO("Not yet implemented")
    }
}
