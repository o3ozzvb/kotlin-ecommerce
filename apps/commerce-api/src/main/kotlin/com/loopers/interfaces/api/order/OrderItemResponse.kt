package com.loopers.interfaces.api.order

import com.loopers.domain.order.OrderItem
import java.math.BigDecimal

data class OrderItemResponse(
    val id: Long?,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
) {
    companion object {
        fun from(orderItem: OrderItem): OrderItemResponse {
            return OrderItemResponse(
                id = orderItem.id,
                productId = orderItem.productId,
                quantity = orderItem.quantity,
                unitPrice = orderItem.unitPrice,
                totalPrice = orderItem.totalPrice
            )
        }
    }
}