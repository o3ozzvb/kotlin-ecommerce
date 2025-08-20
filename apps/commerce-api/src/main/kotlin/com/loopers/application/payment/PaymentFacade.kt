package com.loopers.application.payment

import com.loopers.domain.order.OrderService
import com.loopers.domain.payment.PaymentMethod
import com.loopers.domain.payment.PaymentRequestLog
import com.loopers.domain.payment.PaymentService
import com.loopers.infrastructure.client.PaymentRequest
import com.loopers.infrastructure.client.PgClient
import com.loopers.interfaces.api.payment.PaymentProcessRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentFacade(
    private val paymentService: PaymentService,
    private val orderService: OrderService,
    private val pgClient: PgClient,
) {

    @Transactional
    fun processPayment(userId: String, request: PaymentProcessRequest): PaymentRequestLog {
        // 1. 주문 검증
        val order = orderService.findOrder(userId, request.orderId)

        // 2. PG 결제 요청
        val pgRequest = PaymentRequest.from(order.id!!, request)
        
        val pgResponse = pgClient.requestPayment(pgRequest)
        
        // 3. 결제 정보 저장
        val paymentRequestLog = PaymentRequestLog.create(
            userId = userId,
            orderId = order.id,
            payAmount = request.amount,
            paymentMethod = PaymentMethod.CARD,
            transactionId = pgResponse.data?.transactionKey
        )
        
        return paymentService.save(paymentRequestLog)
    }
}
