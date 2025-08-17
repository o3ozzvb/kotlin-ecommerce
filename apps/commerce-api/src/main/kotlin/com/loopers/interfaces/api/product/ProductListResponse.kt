package com.loopers.interfaces.api.product

import java.math.BigDecimal

data class ProductListResponse(
    val id: Long,
    val name: String,
    val brandName: String,
    val price: BigDecimal,
    val likeCount: Long,
    val isLiked: Boolean = false,
)
