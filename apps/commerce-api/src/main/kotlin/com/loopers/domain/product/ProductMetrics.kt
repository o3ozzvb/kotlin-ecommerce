package com.loopers.domain.product

import java.time.LocalDateTime

data class ProductMetrics(
    val id: Long? = null,
    val productId: Long,
    val likeCount: Int,
    val salesCount: Int,
    val viewCount: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun initialize(productId: Long): ProductMetrics {
            return ProductMetrics(
                productId = productId,
                likeCount = 0,
                salesCount = 0,
                viewCount = 0,
            )
        }
    }

    fun incrementLike(): ProductMetrics {
        return copy(
            likeCount = likeCount + 1,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun decrementLike(): ProductMetrics {
        return copy(
            likeCount = maxOf(0, likeCount - 1),
            updatedAt = LocalDateTime.now(),
        )
    }

    fun incrementSales(): ProductMetrics {
        return copy(
            salesCount = salesCount + 1,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun incrementView(): ProductMetrics {
        return copy(
            viewCount = viewCount + 1,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun getTotalEngagement(): Int {
        return likeCount + viewCount
    }
}
