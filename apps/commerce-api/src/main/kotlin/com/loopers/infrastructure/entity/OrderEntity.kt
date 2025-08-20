package com.loopers.infrastructure.entity

import com.loopers.domain.order.Order
import com.loopers.domain.order.OrderStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "`order`")
class OrderEntity() : BaseEntity() {

    @Column(name = "member_id", nullable = false)
    var memberId: String = ""

    @OneToMany(mappedBy = "orderEntity", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var orderItems: MutableList<OrderItemEntity> = mutableListOf()

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: OrderStatus = OrderStatus.PENDING

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    var totalAmount: BigDecimal = BigDecimal.ZERO

    @Column(name = "final_amount", nullable = false, precision = 19, scale = 2)
    var finalAmount: BigDecimal = BigDecimal.ZERO

    constructor(
        memberId: String,
        orderItems: List<OrderItemEntity> = emptyList(),
        status: OrderStatus = OrderStatus.PENDING,
        totalAmount: BigDecimal,
        finalAmount: BigDecimal,
    ) : this() {
        this.memberId = memberId
        this.orderItems = orderItems.toMutableList()
        this.status = status
        this.totalAmount = totalAmount
        this.finalAmount = finalAmount
        
        // OrderItem에 Order 참조 설정
        this.orderItems.forEach { it.orderEntity = this }
    }

    fun addOrderItem(orderItem: OrderItemEntity) {
        orderItem.orderEntity = this
        orderItems.add(orderItem)
    }

    fun removeOrderItem(orderItem: OrderItemEntity) {
        orderItems.remove(orderItem)
        orderItem.orderEntity = null
    }

    fun toDomain(): Order {
        return Order(
            id = this.id,
            userId = this.memberId,
            orderItems = this.orderItems.map { it.toDomain() },
            totalAmount = this.totalAmount,
            finalAmount = this.finalAmount,
            createdAt = this.createdAt.toLocalDateTime(),
            updatedAt = this.updatedAt.toLocalDateTime()
        )
    }

    companion object {
        fun from(order: Order): OrderEntity {
            return OrderEntity(
                memberId = order.userId,
                totalAmount = order.totalAmount,
                finalAmount = order.finalAmount
            )
        }
    }
}
