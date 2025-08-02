package com.loopers.domain.product

import java.math.BigDecimal

data class ProductData(
    val id: Long,
    val name: String,
    val brandId: Long,
    val inventoryId: Long,
    val price: BigDecimal,
)
