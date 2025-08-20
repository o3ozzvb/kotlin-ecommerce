package com.loopers.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "product_metric")
class ProductMetricsEntity() : BaseEntity() {

    @Column(name = "product_id", nullable = false, unique = true)
    var productId: Long = 0L

    @Column(name = "like_count", nullable = false)
    var likeCount: Int = 0

    @Column(name = "sales_count", nullable = false)
    var salesCount: Int = 0

    @Column(name = "view_count", nullable = false)
    var viewCount: Int = 0

    constructor(
        productId: Long,
        likeCount: Int = 0,
        salesCount: Int = 0,
        viewCount: Int = 0,
    ) : this() {
        this.productId = productId
        this.likeCount = likeCount
        this.salesCount = salesCount
        this.viewCount = viewCount
    }
}
