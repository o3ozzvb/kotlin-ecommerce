package com.loopers.repository

import com.loopers.domain.LikeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LikeJpaRepository : JpaRepository<LikeEntity, Long> {
    fun findByMemberIdAndProductId(memberId: Long, productId: Long): LikeEntity?
    fun findAllByMemberId(memberId: Long): List<LikeEntity>
    fun findAllByProductId(productId: Long): List<LikeEntity>
    fun existsByMemberIdAndProductId(memberId: Long, productId: Long): Boolean
}
