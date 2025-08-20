package com.loopers.interfaces.api.payment

import com.loopers.domain.payment.PaymentRequestLog
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentCreateRequest(
    val orderId: Long,
    val payAmount: BigDecimal,
    val paymentMethod: String,
)

data class PaymentResponse(
    val id: Long,
    val userId: String,
    val orderId: Long,
    val payAmount: BigDecimal,
    val paymentMethod: String,
    val status: String,
    val transactionId: String?,
    val failureReason: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(paymentRequestLog: PaymentRequestLog): PaymentResponse {
            return PaymentResponse(
                id = paymentRequestLog.id!!,
                userId = paymentRequestLog.userId,
                orderId = paymentRequestLog.orderId,
                payAmount = paymentRequestLog.payAmount,
                paymentMethod = paymentRequestLog.paymentMethod.name,
                status = paymentRequestLog.status.name,
                transactionId = paymentRequestLog.transactionId,
                failureReason = paymentRequestLog.failureReason,
                createdAt = paymentRequestLog.createdAt,
                updatedAt = paymentRequestLog.updatedAt,
            )
        }
    }
}

data class PaymentListResponse(
    val payments: List<PaymentResponse>,
    val totalCount: Int,
) {
    companion object {
        fun from(paymentRequestLogs: List<PaymentRequestLog>): PaymentListResponse {
            return PaymentListResponse(
                payments = paymentRequestLogs.map { PaymentResponse.from(it) },
                totalCount = paymentRequestLogs.size,
            )
        }
    }
}
