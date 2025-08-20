package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderJpaRepository : JpaRepository<OrderEntity, Long>