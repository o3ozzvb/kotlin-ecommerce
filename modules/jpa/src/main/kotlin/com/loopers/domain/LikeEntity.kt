package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "like",
    uniqueConstraints = [UniqueConstraint(columnNames = ["member_id", "product_id"])],
)
data class LikeEntity(
    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "product_id", nullable = false)
    val productId: Long,
) : BaseEntity()
