package com.loopers.infrastructure.repository

import com.loopers.domain.order.Order
import com.loopers.domain.order.OrderRepository
import com.loopers.infrastructure.entity.OrderEntity
import org.springframework.stereotype.Repository

@Repository
class OrderRepositoryImpl(
    private val orderJpaRepository: OrderJpaRepository,
) : OrderRepository {

    override fun save(order: Order): Order {
        val entity = OrderEntity.from(order)
        val savedEntity = orderJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): Order? {
        return orderJpaRepository.findById(id)
            .orElse(null)
            ?.toDomain()
    }
}
