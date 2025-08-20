package com.loopers.infrastructure.entity

import com.loopers.domain.order.OrderItem
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "order_item")
class OrderItemEntity() : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var orderEntity: OrderEntity? = null

    @Column(name = "product_id", nullable = false)
    var productId: Long = 0

    @Column(name = "quantity", nullable = false)
    var quantity: Int = 0

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    var unitPrice: BigDecimal = BigDecimal.ZERO

    @Column(name = "total_price", nullable = false, precision = 19, scale = 2)
    var totalPrice: BigDecimal = BigDecimal.ZERO

    constructor(
        productId: Long,
        quantity: Int,
        unitPrice: BigDecimal,
        totalPrice: BigDecimal,
    ) : this() {
        this.productId = productId
        this.quantity = quantity
        this.unitPrice = unitPrice
        this.totalPrice = totalPrice
    }

    constructor(
        orderEntity: OrderEntity,
        productId: Long,
        quantity: Int,
        unitPrice: BigDecimal,
        totalPrice: BigDecimal,
    ) : this() {
        this.orderEntity = orderEntity
        this.productId = productId
        this.quantity = quantity
        this.unitPrice = unitPrice
        this.totalPrice = totalPrice
    }

    fun toDomain(): OrderItem {
        return OrderItem(
            id = this.id,
            productId = this.productId,
            quantity = this.quantity,
            unitPrice = this.unitPrice,
            totalPrice = this.totalPrice
        )
    }
}