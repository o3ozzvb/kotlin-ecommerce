package com.loopers.infrastructure.entity

import com.loopers.domain.payment.PaymentMethod
import com.loopers.domain.payment.PaymentRequestLog
import com.loopers.domain.payment.PaymentStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "payment")
class PaymentEntity() : BaseEntity() {

    @Column(name = "user_id", nullable = false)
    var userId: String = ""

    @Column(name = "order_id", nullable = false)
    var orderId: Long = 0L

    @Column(name = "pay_amount", nullable = false)
    var payAmount: BigDecimal = BigDecimal.ZERO

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    var paymentMethod: PaymentMethod = PaymentMethod.POINT

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: PaymentStatus = PaymentStatus.PENDING

    @Column(name = "transaction_id")
    var transactionId: String? = null

    @Column(name = "failure_reason")
    var failureReason: String? = null

    constructor(
        userId: String,
        orderId: Long,
        payAmount: BigDecimal,
        paymentMethod: PaymentMethod,
        status: PaymentStatus = PaymentStatus.PENDING,
        transactionId: String? = null,
        failureReason: String? = null,
    ) : this() {
        this.userId = userId
        this.orderId = orderId
        this.payAmount = payAmount
        this.paymentMethod = paymentMethod
        this.status = status
        this.transactionId = transactionId
        this.failureReason = failureReason
    }

    fun toDomain(): PaymentRequestLog {
        return PaymentRequestLog(
            id = this.id,
            userId = this.userId,
            orderId = this.orderId,
            payAmount = this.payAmount,
            paymentMethod = this.paymentMethod,
            status = this.status,
            transactionId = this.transactionId,
            failureReason = this.failureReason,
            createdAt = this.createdAt.toLocalDateTime(),
            updatedAt = this.updatedAt.toLocalDateTime()
        )
    }

    companion object {
        fun from(paymentRequestLog: PaymentRequestLog): PaymentEntity {
            return PaymentEntity(
                userId = paymentRequestLog.userId,
                orderId = paymentRequestLog.orderId,
                payAmount = paymentRequestLog.payAmount,
                paymentMethod = paymentRequestLog.paymentMethod,
                status = paymentRequestLog.status,
                transactionId = paymentRequestLog.transactionId,
                failureReason = paymentRequestLog.failureReason
            )
        }
    }
}
