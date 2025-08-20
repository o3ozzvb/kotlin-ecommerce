package com.loopers.interfaces.api.payment

import com.loopers.application.payment.PaymentFacade
import com.loopers.interfaces.api.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController(
    private val paymentFacade: PaymentFacade,
) {
    @PostMapping("/process")
    @ResponseStatus(HttpStatus.CREATED)
    fun processPayment(
        @RequestHeader("X-USER-ID") userId: String,
        @RequestBody request: PaymentProcessRequest,
    ): ApiResponse<PaymentResponse> {
        val payment = paymentFacade.processPayment(userId, request)
        return ApiResponse.success(PaymentResponse.from(payment))
    }
}
