package com.loopers.infrastructure.repository

import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductCacheRepository
import com.loopers.domain.product.ProductData
import com.loopers.support.cache.CacheRepository
import com.loopers.domain.product.ProductListCacheKey
import org.springframework.data.domain.Page
import org.springframework.stereotype.Repository

@Repository
class ProductCacheRepositoryImpl(
    private val cacheRepository: CacheRepository,
) : ProductCacheRepository {
    override fun getProductList(cacheKey: ProductListCacheKey): Page<ProductData>? {
        val key = cacheKey.toRedisKey()
        return cacheRepository.get(key, Page::class.java) as? Page<ProductData>
    }

    override fun setProductList(cacheKey: ProductListCacheKey, data: Page<ProductData>) {
        val key = cacheKey.toRedisKey()
//        val ttl = CacheTTL.getProductListTTL(cacheKey)
        cacheRepository.set(key, data)
    }

    override fun getProductDetail(productId: Long): Product? {
        val key = "product:detail:$productId"
        return cacheRepository.get(key, Product::class.java)
    }

    override fun setProductDetail(product: Product) {
        val key = "product:detail:${product.id}"
        cacheRepository.set(key, product)
    }

//    override fun evictProductList(sortBy: String, page: Int?, categoryId: Long?, brandId: Long?) {
//        val cacheKey = ProductListCacheKey(sortBy, page, categoryId, brandId)
//        val key = cacheKey.toRedisKey()
//        cacheRepository.delete(key)
//    }
//
//    override fun evictProductDetail(productId: Long) {
//        val key = "product:detail:$productId"
//        cacheRepository.delete(key)
//    }
//
//    override fun evictAllProductCaches(): Long {
//        return cacheRepository.deleteByPattern("product:*")
//    }
}
