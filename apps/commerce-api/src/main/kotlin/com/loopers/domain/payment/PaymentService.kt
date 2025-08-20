package com.loopers.domain.payment

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PaymentService(
    private val paymentRepository: PaymentRepository,
) {

    fun save(paymentRequestLog: PaymentRequestLog): PaymentRequestLog {
        return paymentRepository.save(paymentRequestLog)
    }

    fun processPayment(paymentId: Long, transactionId: String): PaymentRequestLog {
        val payment = getPaymentById(paymentId)
        val processedPayment = payment.process(transactionId)
        return paymentRepository.save(processedPayment)
    }

    fun completePayment(paymentId: Long): PaymentRequestLog {
        val payment = getPaymentById(paymentId)
        val completedPayment = payment.complete()
        return paymentRepository.save(completedPayment)
    }

    fun failPayment(paymentId: Long, reason: String): PaymentRequestLog {
        val payment = getPaymentById(paymentId)
        val failedPayment = payment.fail(reason)
        return paymentRepository.save(failedPayment)
    }

    fun cancelPayment(paymentId: Long): PaymentRequestLog {
        val payment = getPaymentById(paymentId)
        
        if (!payment.canCancel()) {
            throw CoreException(
                errorType = ErrorType.PAYMENT_CANNOT_CANCEL,
                customMessage = "Payment cannot be cancelled. Current status: ${payment.status}"
            )
        }
        
        val cancelledPayment = payment.cancel()
        return paymentRepository.save(cancelledPayment)
    }

    fun refundPayment(paymentId: Long): PaymentRequestLog {
        val payment = getPaymentById(paymentId)
        
        if (!payment.canRefund()) {
            throw CoreException(
                errorType = ErrorType.PAYMENT_CANNOT_REFUND,
                customMessage = "Payment cannot be refunded. Current status: ${payment.status}"
            )
        }
        
        val refundedPayment = payment.refund()
        return paymentRepository.save(refundedPayment)
    }

    @Transactional(readOnly = true)
    fun getPaymentById(paymentId: Long): PaymentRequestLog {
        return paymentRepository.findById(paymentId)
            ?: throw CoreException(
                errorType = ErrorType.PAYMENT_NOT_FOUND,
                customMessage = "Payment not found with id: $paymentId"
            )
    }
}
