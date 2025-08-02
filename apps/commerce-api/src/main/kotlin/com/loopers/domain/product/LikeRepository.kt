package com.loopers.domain.product

interface LikeRepository {
    fun save(like: Like): Like
    fun findById(id: Long): Like?
    fun findByMemberIdAndProductId(memberId: Long, productId: Long): Like?
    fun findAllByMemberId(memberId: Long): List<Like>
    fun findAllByProductId(productId: Long): List<Like>
    fun countByProductId(productId: Long): Long
    fun existsByMemberIdAndProductId(memberId: Long, productId: Long): Boolean
    fun deleteById(id: Long)
}
