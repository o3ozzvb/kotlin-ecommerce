package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.ProductMetricsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductMetricsJpaRepository : JpaRepository<ProductMetricsEntity, Long> {
    fun findByProductId(productId: Long): ProductMetricsEntity?
    fun findTop10ByOrderByLikeCountDesc(): List<ProductMetricsEntity>
    fun findTop10ByOrderBySalesCountDesc(): List<ProductMetricsEntity>
}
