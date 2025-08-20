package com.loopers.application.order

import com.loopers.domain.inventory.InventoryService
import com.loopers.domain.order.Order
import com.loopers.domain.order.OrderItem
import com.loopers.domain.order.OrderService
import com.loopers.domain.product.ProductService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderFacade(
    private val orderService: OrderService,
    private val productService: ProductService,
    private val inventoryService: InventoryService,
) {

    @Transactional
    fun createOrder(userId: String, orderItems: List<OrderItem>): Order {
        // 1. 상품 존재 여부 확인 및 재고 차감 (실패하면 여기서 예외)
        orderItems.forEach { orderItem ->
            // 상품 존재 확인
            val product = productService.find(orderItem.productId)
            
            // 재고 차감 (재고 부족시 예외 발생)
            inventoryService.consumeStock(product.inventoryId, orderItem.quantity)
        }

        // 2. 주문 생성 (재고 차감이 성공한 후)
        val order = orderService.createOrder(userId, orderItems)

        return order
    }
}
