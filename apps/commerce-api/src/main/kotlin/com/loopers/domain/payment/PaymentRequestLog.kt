package com.loopers.domain.payment

import com.loopers.infrastructure.entity.PaymentEntity
import java.math.BigDecimal
import java.time.LocalDateTime

data class PaymentRequestLog(
    val id: Long? = null,
    val userId: String,
    val orderId: Long,
    val payAmount: BigDecimal,
    val paymentMethod: PaymentMethod,
    val status: PaymentStatus = PaymentStatus.PENDING,
    val transactionId: String? = null,
    val failureReason: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {

    fun process(transactionId: String): PaymentRequestLog {
        return copy(
            status = PaymentStatus.PROCESSING,
            transactionId = transactionId,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun complete(): PaymentRequestLog {
        return copy(
            status = PaymentStatus.COMPLETED,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun fail(reason: String): PaymentRequestLog {
        return copy(
            status = PaymentStatus.FAILED,
            failureReason = reason,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun cancel(): PaymentRequestLog {
        return copy(
            status = PaymentStatus.CANCELLED,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun refund(): PaymentRequestLog {
        return copy(
            status = PaymentStatus.REFUNDED,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun isCompleted(): Boolean = status == PaymentStatus.COMPLETED

    fun canCancel(): Boolean = status in listOf(PaymentStatus.PENDING, PaymentStatus.PROCESSING)

    fun canRefund(): Boolean = status == PaymentStatus.COMPLETED

    companion object {
        fun create(
            userId: String,
            orderId: Long,
            payAmount: BigDecimal,
            paymentMethod: PaymentMethod,
            transactionId: String? = null
        ): PaymentRequestLog {
            return PaymentRequestLog(
                userId = userId,
                orderId = orderId,
                payAmount = payAmount,
                paymentMethod = paymentMethod,
                transactionId = transactionId,
            )
        }

        fun from(entity: PaymentEntity): PaymentRequestLog {
            return PaymentRequestLog(
                id = entity.id,
                userId = entity.userId,
                orderId = entity.orderId,
                payAmount = entity.payAmount,
                paymentMethod = PaymentMethod.valueOf(entity.paymentMethod.name),
                status = PaymentStatus.valueOf(entity.status.name),
                transactionId = entity.transactionId,
                failureReason = entity.failureReason,
                createdAt = entity.createdAt.toLocalDateTime(),
                updatedAt = entity.updatedAt.toLocalDateTime(),
            )
        }
    }
}
