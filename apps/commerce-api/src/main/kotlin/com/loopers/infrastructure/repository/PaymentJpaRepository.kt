package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.PaymentEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PaymentJpaRepository : JpaRepository<PaymentEntity, Long>