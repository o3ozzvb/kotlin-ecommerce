package com.loopers.infrastructure.product

import com.loopers.infrastructure.persistence.product.InventoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryJpaRepository : JpaRepository<InventoryEntity, Long> {
    fun findByAvailableStockGreaterThan(minStock: Int): List<InventoryEntity>
}
