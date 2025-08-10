package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "product_metric")
data class ProductMetricsEntity(
    @Column(name = "product_id", nullable = false, unique = true)
    val productId: Long,

    @Column(name = "like_count", nullable = false)
    val likeCount: Int = 0,

    @Column(name = "sale_count", nullable = false)
    val saleCount: Int = 0,
) : BaseEntity()
