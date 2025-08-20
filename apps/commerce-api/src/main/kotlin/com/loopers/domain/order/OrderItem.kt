package com.loopers.domain.order

import com.loopers.domain.product.Product
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderItem(
    val id: Long? = null,
    val orderId: Long? = null,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val totalPrice: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    
    companion object {
        fun create(product: Product, quantity: Int): OrderItem {
            require(quantity > 0) { "주문 수량은 0보다 커야 합니다." }
            require(product.isAvailable()) { "재고가 부족한 상품입니다." }
            
            val totalPrice = product.price.multiply(BigDecimal.valueOf(quantity.toLong()))
            
            return OrderItem(
                productId = product.id,
                quantity = quantity,
                unitPrice = product.price,
                totalPrice = totalPrice,
            )
        }
        
        fun create(productId: Long, quantity: Int, unitPrice: BigDecimal): OrderItem {
            require(quantity > 0) { "주문 수량은 0보다 커야 합니다." }
            
            val totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity.toLong()))
            
            return OrderItem(
                productId = productId,
                quantity = quantity,
                unitPrice = unitPrice,
                totalPrice = totalPrice,
            )
        }
    }
    
    fun updateQuantity(newQuantity: Int): OrderItem {
        require(newQuantity > 0) { "주문 수량은 0보다 커야 합니다." }
        
        val newTotalPrice = unitPrice.multiply(BigDecimal.valueOf(newQuantity.toLong()))
        
        return copy(
            quantity = newQuantity,
            totalPrice = newTotalPrice,
            updatedAt = LocalDateTime.now()
        )
    }
    
    fun updateUnitPrice(newUnitPrice: BigDecimal): OrderItem {
        val newTotalPrice = newUnitPrice.multiply(BigDecimal.valueOf(quantity.toLong()))
        
        return copy(
            unitPrice = newUnitPrice,
            totalPrice = newTotalPrice,
            updatedAt = LocalDateTime.now()
        )
    }
    
    fun calculateTotalPrice(): BigDecimal {
        return unitPrice.multiply(BigDecimal.valueOf(quantity.toLong()))
    }
}