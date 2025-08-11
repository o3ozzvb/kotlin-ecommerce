package com.loopers.domain

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

    constructor(
        name: String,
        brandId: Long,
        inventoryId: Long,
        price: BigDecimal,
    ) : this() {
        this.name = name
        this.brandId = brandId
        this.inventoryId = inventoryId
        this.price = price
    }
}
