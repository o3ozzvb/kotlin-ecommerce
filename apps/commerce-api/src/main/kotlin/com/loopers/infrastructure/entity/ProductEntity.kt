package com.loopers.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
class ProductEntity() : BaseEntity() {

    @Column(nullable = false, length = 200)
    var name: String = ""

    @Column(name = "brand_id", nullable = false)
    var brandId: Long = 0L

    @Column(name = "inventory_id", nullable = false)
    var inventoryId: Long = 0L

    @Column(nullable = false)
    var price: BigDecimal = BigDecimal.ZERO

    @Column(name = "like_count", nullable = false)
    var likeCount: Long = 0L

    constructor(
        name: String,
        brandId: Long,
        inventoryId: Long,
        price: BigDecimal,
        likeCount: Long = 0L,
    ) : this() {
        this.name = name
        this.brandId = brandId
        this.inventoryId = inventoryId
        this.price = price
        this.likeCount = likeCount
    }
}
