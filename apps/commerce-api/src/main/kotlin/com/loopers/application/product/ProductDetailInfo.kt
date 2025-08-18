package com.loopers.application.product

import com.loopers.domain.product.Product

data class ProductDetailInfo(
    val product: Product,
    val likeCount: Int,
    val isLiked: Boolean,
)
