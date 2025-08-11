package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "`like`",
    uniqueConstraints = [UniqueConstraint(columnNames = ["member_id", "product_id"])],
)
class LikeEntity() : BaseEntity() {

    @Column(name = "member_id", nullable = false)
    var memberId: Long = 0L

    @Column(name = "product_id", nullable = false)
    var productId: Long = 0L

    constructor(
        memberId: Long,
        productId: Long,
    ) : this() {
        this.memberId = memberId
        this.productId = productId
    }
}
