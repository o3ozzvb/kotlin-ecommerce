package com.loopers.infrastructure.repository

import com.loopers.domain.inventory.Inventory
import com.loopers.domain.inventory.InventoryRepository
import com.loopers.infrastructure.persistence.inventory.InventoryEntity
import com.loopers.infrastructure.persistence.inventory.InventoryJpaRepository
import org.springframework.stereotype.Repository

@Repository
class InventoryRepositoryImpl(
    private val inventoryJpaRepository: InventoryJpaRepository,
) : InventoryRepository {

    override fun save(inventory: Inventory): Inventory {
        val entity = if (inventory.id != 0L) {
            // Update existing entity
            val existingEntity = inventoryJpaRepository.findById(inventory.id).orElse(InventoryEntity())
            existingEntity.apply {
                this.totalStock = inventory.totalStock
                this.actualStock = inventory.actualStock
                this.availableStock = inventory.availableStock
            }
        } else {
            // Create new entity
            InventoryEntity(
                totalStock = inventory.totalStock,
                actualStock = inventory.actualStock,
                availableStock = inventory.availableStock,
            )
        }
        val savedEntity = inventoryJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): Inventory? {
        return inventoryJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findAll(): List<Inventory> {
        return inventoryJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun deleteById(id: Long) {
        inventoryJpaRepository.deleteById(id)
    }

    override fun findByAvailableStockGreaterThan(minStock: Int): List<Inventory> {
        return inventoryJpaRepository.findByAvailableStockGreaterThan(minStock)
            .map { it.toDomain() }
    }

    // Entity -> Domain 변환
    private fun InventoryEntity.toDomain(): Inventory = Inventory(
        id = this.id,
        totalStock = this.totalStock,
        actualStock = this.actualStock,
        availableStock = this.availableStock,
    )
}
