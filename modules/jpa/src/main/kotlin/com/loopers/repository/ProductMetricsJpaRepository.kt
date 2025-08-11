package com.loopers.repository

import com.loopers.domain.ProductMetricsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductMetricsJpaRepository : JpaRepository<ProductMetricsEntity, Long> {
    fun findByProductId(productId: Long): ProductMetricsEntity?
    fun findTop10ByOrderByLikeCountDesc(): List<ProductMetricsEntity>
    fun findTop10ByOrderBySalesCountDesc(): List<ProductMetricsEntity>
}
