package com.loopers.interfaces.api.payment

import java.math.BigDecimal

data class PaymentProcessRequest(
    val orderId: Long,
    val cardType: String,
    val cardNo: String,
    val amount: BigDecimal,
    val callbackUrl: String
)