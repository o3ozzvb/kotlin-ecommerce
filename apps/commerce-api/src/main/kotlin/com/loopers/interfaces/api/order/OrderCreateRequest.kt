package com.loopers.interfaces.api.order

import com.loopers.domain.order.OrderItem
import java.math.BigDecimal

data class OrderCreateRequest(
    val orderItems: List<OrderItemRequest>
) {
    data class OrderItemRequest(
        val productId: Long,
        val quantity: Int,
        val unitPrice: BigDecimal
    ) {
        fun toOrderItem(): OrderItem {
            return OrderItem.create(
                productId = productId,
                quantity = quantity,
                unitPrice = unitPrice
            )
        }
    }
    
    fun toOrderItems(): List<OrderItem> {
        return orderItems.map { it.toOrderItem() }
    }
}