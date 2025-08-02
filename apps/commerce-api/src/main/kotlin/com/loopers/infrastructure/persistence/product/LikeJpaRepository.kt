package com.loopers.infrastructure.persistence.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LikeJpaRepository : JpaRepository<LikeEntity, Long> {

    fun findByMemberIdAndProductId(memberId: Long, productId: Long): LikeEntity?

    fun findAllByMemberId(memberId: Long): List<LikeEntity>

    fun findAllByProductId(productId: Long): List<LikeEntity>

    @Query("SELECT COUNT(l) FROM LikeEntity l WHERE l.productId = :productId")
    fun countByProductId(productId: Long): Long

    fun existsByMemberIdAndProductId(memberId: Long, productId: Long): Boolean
}
