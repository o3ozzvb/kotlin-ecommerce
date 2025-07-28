package com.loopers.domain.product

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "product_metrics")
class ProductMetrics(
    productId: Long,
    likeCount: Int,
    salesCount: Int,
) : BaseEntity() {
    @Column(name = "product_id", nullable = false)
    var productId: Long? = null
        protected set

    @Column(name = "like_count", nullable = false)
    var likeCount: Int? = null
        protected set

    @Column(name = "sales_count", nullable = false)
    var salesCount: Int? = null
        protected set
}
