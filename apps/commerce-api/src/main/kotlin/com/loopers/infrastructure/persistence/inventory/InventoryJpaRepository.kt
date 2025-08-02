package com.loopers.infrastructure.persistence.inventory

import org.springframework.data.jpa.repository.JpaRepository

interface InventoryJpaRepository : JpaRepository<InventoryEntity, Long> {
    fun findByAvailableStockGreaterThan(minStock: Int): List<InventoryEntity>
}
