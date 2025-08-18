package com.loopers.domain.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductService(
    private val productRepository: ProductRepository,
    private val productCacheRepository: ProductCacheRepository,
) {
    fun findProductsData(brandId: Long?, sortBy: String, pageable: Pageable): Page<ProductData> {
        // 캐시에서 조회
        val cacheKey = ProductListCacheKey(brandId, sortBy, pageable.pageNumber, pageable.pageSize)
        productCacheRepository.getProductList(cacheKey)?.let { return it }

        // 캐시 미스 시 DB 조회
        val result = productRepository.findProductsData(brandId, sortBy, pageable)

        // 결과 캐시에 저장
        productCacheRepository.setProductList(cacheKey, result)

        return result
    }

    fun findById(id: Long): ProductData? {
        return productRepository.findById(id)
    }
}
