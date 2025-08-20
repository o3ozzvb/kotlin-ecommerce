package com.loopers.infrastructure.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "pgClient",
    url = "\${pg-simulator.url:http://localhost:8081}",
    configuration = [PgClientConfig::class]
)
interface PgClient {
    
    @PostMapping("/api/v1/payments")
    fun requestPayment(
        @RequestBody request: PaymentRequest
    ): ApiResponse<TransactionResponse>
    
    @GetMapping("/api/v1/payments/{transactionKey}")
    fun getTransaction(
        @PathVariable transactionKey: String
    ): ApiResponse<TransactionDetailResponse>
    
    @GetMapping("/api/v1/payments")
    fun getTransactionsByOrder(
        @RequestParam orderId: String
    ): ApiResponse<OrderResponse>
}
