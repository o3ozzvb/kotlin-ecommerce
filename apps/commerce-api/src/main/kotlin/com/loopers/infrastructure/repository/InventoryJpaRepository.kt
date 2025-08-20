package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.InventoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryJpaRepository : JpaRepository<InventoryEntity, Long> {
    fun findByAvailableStockGreaterThan(minStock: Int): List<InventoryEntity>
}
