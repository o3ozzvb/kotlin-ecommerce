package com.loopers.infrastructure.persistence.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ProductMetricsJpaRepository : JpaRepository<ProductMetricsEntity, Long> {

    fun findByProductId(productId: Long): ProductMetricsEntity?

    @Modifying
    @Query("UPDATE ProductMetricsEntity p SET p.likeCount = p.likeCount + 1 WHERE p.productId = :productId")
    fun incrementLikeCount(productId: Long): Int

    @Modifying
    @Query("UPDATE ProductMetricsEntity p SET p.likeCount = GREATEST(0, p.likeCount - 1) WHERE p.productId = :productId")
    fun decrementLikeCount(productId: Long): Int

    @Modifying
    @Query("UPDATE ProductMetricsEntity p SET p.salesCount = p.salesCount + 1 WHERE p.productId = :productId")
    fun incrementSalesCount(productId: Long): Int

    @Modifying
    @Query("UPDATE ProductMetricsEntity p SET p.viewCount = p.viewCount + 1 WHERE p.productId = :productId")
    fun incrementViewCount(productId: Long): Int

    @Query("SELECT p FROM ProductMetricsEntity p ORDER BY p.likeCount DESC")
    fun findTopByLikeCount(): List<ProductMetricsEntity>

    @Query("SELECT p FROM ProductMetricsEntity p ORDER BY p.salesCount DESC")
    fun findTopBySalesCount(): List<ProductMetricsEntity>
}
