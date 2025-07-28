package com.loopers.domain.product

import com.loopers.domain.BaseEntity
import com.loopers.domain.member.Member
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "like")
class Like(
    member: Member,
    product: Product,
    ) : BaseEntity() {
    @Column(name = "member_id", nullable = false)
    var memberId: Long = member.id

    @Column(name = "product_id", nullable = false)
    var productId: Long = product.id
}
