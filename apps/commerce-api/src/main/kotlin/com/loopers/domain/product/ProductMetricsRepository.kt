package com.loopers.domain.product

interface ProductMetricsRepository {
    fun save(productMetrics: ProductMetrics): ProductMetrics
    fun findById(id: Long): ProductMetrics?
    fun findByProductId(productId: Long): ProductMetrics?
    fun findOrCreateByProductId(productId: Long): ProductMetrics
    fun deleteById(id: Long)
    fun findTopByLikeCount(): List<ProductMetrics>
    fun findTopBySalesCount(): List<ProductMetrics>
}
