package com.loopers.interfaces.api.order

import com.loopers.domain.order.Order
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val id: Long,
    val userId: String,
    val orderItems: List<OrderItemResponse>,
    val status: String,
    val totalAmount: BigDecimal,
    val finalAmount: BigDecimal,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(order: Order): OrderResponse {
            return OrderResponse(
                id = order.id!!,
                userId = order.userId,
                orderItems = order.orderItems.map { OrderItemResponse.from(it) },
                status = order.status.name,
                totalAmount = order.totalAmount,
                finalAmount = order.finalAmount,
                createdAt = order.createdAt,
                updatedAt = order.updatedAt
            )
        }
    }
}
