package com.loopers.interfaces.api.order

import com.loopers.domain.order.Order

data class OrderListResponse(
    val orders: List<OrderResponse>,
    val totalCount: Int
) {
    companion object {
        fun from(orders: List<Order>): OrderListResponse {
            return OrderListResponse(
                orders = orders.map { OrderResponse.from(it) },
                totalCount = orders.size
            )
        }
    }
}