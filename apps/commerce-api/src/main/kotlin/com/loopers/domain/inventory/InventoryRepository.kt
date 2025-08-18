package com.loopers.domain.inventory

interface InventoryRepository {
    fun save(inventory: Inventory): Inventory
    fun findById(id: Long): Inventory?
    fun findAll(): List<Inventory>
    fun deleteById(id: Long)
    fun findByAvailableStockGreaterThan(minStock: Int): List<Inventory>
}
