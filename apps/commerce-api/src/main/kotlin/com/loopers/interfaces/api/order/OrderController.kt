package com.loopers.interfaces.api.order

import com.loopers.application.order.OrderFacade
import com.loopers.interfaces.api.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
    private val orderFacade: OrderFacade,
) {

    @PostMapping
    fun createOrder(
        @RequestHeader("X-USER-ID") userId: String,
        @RequestBody request: OrderCreateRequest
    ): ApiResponse<OrderResponse> {
        val order = orderFacade.createOrder(userId, request.toOrderItems())
        return ApiResponse.success(OrderResponse.from(order))
    }
}
