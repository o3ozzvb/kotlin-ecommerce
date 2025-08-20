package com.loopers.infrastructure.client

data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: ErrorResponse?
)

data class ErrorResponse(
    val code: String,
    val message: String
)

data class PaymentRequest(
    val orderId: String,
    val cardType: CardType,
    val cardNo: String,
    val amount: Long,
    val callbackUrl: String,
) {
    companion object {
        fun from(orderId: Long, request: com.loopers.interfaces.api.payment.PaymentProcessRequest): PaymentRequest {
            return PaymentRequest(
                orderId = orderId.toString(),
                cardType = CardType.valueOf(request.cardType),
                cardNo = request.cardNo,
                amount = request.amount.toLong(),
                callbackUrl = request.callbackUrl
            )
        }
    }
}

data class TransactionResponse(
    val transactionKey: String,
    val status: TransactionStatus,
    val reason: String?,
)

data class TransactionDetailResponse(
    val transactionKey: String,
    val orderId: String,
    val cardType: CardType,
    val cardNo: String,
    val amount: Long,
    val status: TransactionStatus,
    val reason: String?,
)

data class OrderResponse(
    val orderId: String,
    val transactions: List<TransactionResponse>,
)

enum class CardType {
    SAMSUNG,
    KB,
    HYUNDAI,
}

enum class TransactionStatus {
    PENDING,
    SUCCESS,
    FAILED,
}
