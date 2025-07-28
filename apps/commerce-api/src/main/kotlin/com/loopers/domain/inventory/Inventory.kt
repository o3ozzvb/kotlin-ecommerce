package com.loopers.domain.inventory

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

data class Inventory(
    val id: Long,
    private var totalStock: Int,
    private var actualStock: Int,
    private var availableStock: Int,
) {
    fun consume(quantity: Int): Inventory {
        require(quantity > 0) { "소비 수량은 0보다 커야 합니다." }
        require(availableStock >= quantity) { "재고가 부족합니다. 요청: $quantity, 가용: $availableStock" }

        return copy(
            totalStock = totalStock - quantity,
            availableStock = availableStock - quantity,
            actualStock = actualStock - quantity,
        )
    }

    fun add(quantity: Int): Inventory {
        require(quantity > 0) { "추가 수량은 0보다 커야 합니다." }

        return copy(
            totalStock = totalStock + quantity,
            availableStock = availableStock + quantity,
            actualStock = actualStock + quantity,
        )
    }

    fun reserve(quantity: Int): Inventory {
        return copy(
            availableStock = availableStock - quantity,
        )
    }

    fun isAvailable(): Boolean {
        return availableStock > 0
    }
}
