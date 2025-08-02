package com.loopers.domain.product

import java.time.LocalDateTime

data class Like(
    val id: Long? = null,
    val memberId: Long,
    val productId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun create(memberId: Long, productId: Long): Like {
            return Like(
                memberId = memberId,
                productId = productId,
            )
        }
    }

    fun isSameUser(memberId: Long): Boolean {
        return this.memberId == memberId
    }

    fun isSameProduct(productId: Long): Boolean {
        return this.productId == productId
    }
}
