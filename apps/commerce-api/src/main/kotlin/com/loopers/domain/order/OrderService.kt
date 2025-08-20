package com.loopers.domain.order

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {
    @Transactional
    fun createOrder(userId: String, orderItems: List<OrderItem>): Order {
        val order = Order.from(
            userId = userId,
            orderItems = orderItems
        )
        return orderRepository.save(order)
    }

    @Transactional(readOnly = true)
    fun findOrder(userId: String, orderId: Long): Order {
        val order = orderRepository.findById(orderId)
            ?: throw CoreException(ErrorType.NOT_FOUND, "주문을 찾을 수 없습니다. ID: $orderId")
        
        if (order.userId != userId) {
            throw CoreException(ErrorType.FORBIDDEN, "주문에 대한 권한이 없습니다.")
        }
        
        return order
    }
}
