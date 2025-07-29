package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "inventory")
data class InventoryEntity(
    @Column(name = "total_stock", nullable = false)
    val totalStock: Int,

    @Column(name = "actual_stock", nullable = false)
    val actualStock: Int,

    @Column(name = "available_stock", nullable = false)
    val availableStock: Int,
) : BaseEntity()
