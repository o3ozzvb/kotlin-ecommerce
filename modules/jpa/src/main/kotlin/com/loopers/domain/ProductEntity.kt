package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
data class ProductEntity(
    @Column(nullable = false, length = 200)
    val name: String,

    @Column(name = "brand_id", nullable = false)
    val brandId: Long,

    @Column(name = "inventory_id", nullable = false)
    val inventoryId: Long,

    @Column(nullable = false)
    val price: BigDecimal,
) : BaseEntity()
