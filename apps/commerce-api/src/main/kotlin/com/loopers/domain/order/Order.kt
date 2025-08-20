package com.loopers.domain.order

import com.loopers.infrastructure.entity.OrderEntity
import java.math.BigDecimal
import java.time.LocalDateTime

data class Order(
    val id: Long? = null,
    val userId: String,
    val orderItems: List<OrderItem>,
    val status: OrderStatus = OrderStatus.PENDING,
    val totalAmount: BigDecimal,
    val finalAmount: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun from(
            userId: String,
            orderItems: List<OrderItem>
        ): Order {
            val totalAmount = orderItems.sumOf { it.totalPrice }
            return Order(
                userId = userId,
                orderItems = orderItems,
                totalAmount = totalAmount,
                finalAmount = totalAmount,
            )
        }

        fun from(entity: OrderEntity): Order {
            return Order(
                id = entity.id,
                userId = entity.memberId,
                orderItems = emptyList(),
                totalAmount = entity.totalAmount,
                finalAmount = entity.finalAmount,
                createdAt = entity.createdAt.toLocalDateTime(),
                updatedAt = entity.updatedAt.toLocalDateTime()
            )
        }
    }
}
