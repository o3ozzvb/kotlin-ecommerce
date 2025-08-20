package com.loopers.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "inventory")
class InventoryEntity() : BaseEntity() {

    @Column(name = "total_stock", nullable = false)
    var totalStock: Int = 0

    @Column(name = "actual_stock", nullable = false)
    var actualStock: Int = 0

    @Column(name = "available_stock", nullable = false)
    var availableStock: Int = 0

    constructor(
        totalStock: Int,
        actualStock: Int,
        availableStock: Int,
    ) : this() {
        this.totalStock = totalStock
        this.actualStock = actualStock
        this.availableStock = availableStock
    }
}
