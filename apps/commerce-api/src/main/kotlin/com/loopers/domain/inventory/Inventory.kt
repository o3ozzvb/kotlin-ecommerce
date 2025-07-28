package com.loopers.domain.inventory

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "inventory")
class Inventory(
    totalStock: Int = 0,
    actualStock: Int = 0,
    availableStock: Int = 0,
) : BaseEntity() {
    @Column(name = "total_stock", nullable = false)
    var totalStock: Int = totalStock
        protected set

    @Column(name = "actual_stock", nullable = false)
    var actualStock: Int = actualStock
        protected set

    @Column(name = "available_stock", nullable = false)
    var availableStock: Int = availableStock
        protected set

}
