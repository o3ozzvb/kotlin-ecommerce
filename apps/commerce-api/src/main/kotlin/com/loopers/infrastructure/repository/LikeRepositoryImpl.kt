package com.loopers.infrastructure.repository

import com.loopers.domain.product.Like
import com.loopers.domain.product.LikeRepository
import com.loopers.infrastructure.persistence.product.LikeEntity
import com.loopers.infrastructure.persistence.product.LikeJpaRepository
import org.springframework.stereotype.Repository

@Repository
class LikeRepositoryImpl(
    private val likeJpaRepository: LikeJpaRepository,
) : LikeRepository {

    override fun save(like: Like): Like {
        val entity = if (like.id != null) {
            val existingEntity = likeJpaRepository.findById(like.id).orElse(LikeEntity())
            existingEntity.memberId = like.memberId
            existingEntity.productId = like.productId
            existingEntity
        } else {
            LikeEntity(
                memberId = like.memberId,
                productId = like.productId,
            )
        }
        val savedEntity = likeJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): Like? {
        return likeJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByMemberIdAndProductId(memberId: Long, productId: Long): Like? {
        return likeJpaRepository.findByMemberIdAndProductId(memberId, productId)?.toDomain()
    }

    override fun findAllByMemberId(memberId: Long): List<Like> {
        return likeJpaRepository.findAllByMemberId(memberId)
            .map { it.toDomain() }
    }

    override fun findAllByProductId(productId: Long): List<Like> {
        return likeJpaRepository.findAllByProductId(productId)
            .map { it.toDomain() }
    }

    override fun existsByMemberIdAndProductId(memberId: Long, productId: Long): Boolean {
        return likeJpaRepository.existsByMemberIdAndProductId(memberId, productId)
    }

    override fun deleteById(id: Long) {
        likeJpaRepository.deleteById(id)
    }

    private fun LikeEntity.toDomain(): Like = Like(
        id = this.id,
        memberId = this.memberId,
        productId = this.productId,
        createdAt = this.createdAt,
    )
}
