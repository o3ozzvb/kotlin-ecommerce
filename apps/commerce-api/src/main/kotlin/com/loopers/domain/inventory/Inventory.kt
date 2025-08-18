package com.loopers.domain.inventory

data class Inventory(
    val id: Long,
    val totalStock: Int,
    val actualStock: Int,
    val availableStock: Int,
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
