package com.loopers.domain.inventory

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Service

@Service
class InventoryService(
    private val inventoryRepository: InventoryRepository,
) {
    fun findById(id: Long): Inventory {
        return inventoryRepository.findById(id)
            ?: throw CoreException(ErrorType.NOT_FOUND, "재고를 찾을 수 없습니다. ID: $id")
    }

    fun findAll(): List<Inventory> {
        return inventoryRepository.findAll()
    }

    fun findAvailableInventories(minStock: Int = 0): List<Inventory> {
        return inventoryRepository.findByAvailableStockGreaterThan(minStock)
    }

    fun save(inventory: Inventory): Inventory {
        return inventoryRepository.save(inventory)
    }

    fun deleteById(id: Long) {
        inventoryRepository.deleteById(id)
    }

    fun consumeStock(inventoryId: Long, quantity: Int): Inventory {
        val inventory = findById(inventoryId)
        val updatedInventory = inventory.consume(quantity)
        return save(updatedInventory)
    }

    fun addStock(inventoryId: Long, quantity: Int): Inventory {
        val inventory = findById(inventoryId)
        val updatedInventory = inventory.add(quantity)
        return save(updatedInventory)
    }

    fun reserveStock(inventoryId: Long, quantity: Int): Inventory {
        val inventory = findById(inventoryId)
        val updatedInventory = inventory.reserve(quantity)
        return save(updatedInventory)
    }

    fun isAvailable(inventoryId: Long, quantity: Int) {
        val inventory = findById(inventoryId)
        if (inventory.availableStock < quantity) {
            throw CoreException(ErrorType.BAD_REQUEST, "재고가 부족합니다. 요청: $quantity, 가용: ${inventory.availableStock}")
        }
    }
}
