package com.loopers.infrastructure.repository

import com.loopers.domain.payment.PaymentRequestLog
import com.loopers.domain.payment.PaymentRepository
import com.loopers.infrastructure.entity.PaymentEntity
import org.springframework.stereotype.Repository

@Repository
class PaymentRepositoryImpl(
    private val paymentJpaRepository: PaymentJpaRepository,
) : PaymentRepository {

    override fun save(paymentRequestLog: PaymentRequestLog): PaymentRequestLog {
        val entity = PaymentEntity.from(paymentRequestLog)
        val savedEntity = paymentJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): PaymentRequestLog? {
        return paymentJpaRepository.findById(id)
            .orElse(null)
            ?.toDomain()
    }
}