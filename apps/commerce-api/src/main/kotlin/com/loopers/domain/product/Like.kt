package com.loopers.domain.product

import com.loopers.domain.BaseEntity
import com.loopers.domain.member.Member
import jakarta.persistence.Column

class Like(
    member: Member,
    product: Product,
    ) : BaseEntity() {
    @Column(name = "member_id", nullable = false)
    var memberId: Long = member.id

    @Column(name = "product_id", nullable = false)
    var productId: Long = product.id
}
