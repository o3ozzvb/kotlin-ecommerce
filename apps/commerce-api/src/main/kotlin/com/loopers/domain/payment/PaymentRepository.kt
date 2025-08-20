package com.loopers.domain.payment

interface PaymentRepository {
    fun save(paymentRequestLog: PaymentRequestLog): PaymentRequestLog
    fun findById(id: Long): PaymentRequestLog?
}
