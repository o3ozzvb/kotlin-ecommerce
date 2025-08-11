package com.loopers.domain.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository,
) {
    fun like(memberId: Long, productId: Long): Like {
        val existingLike = likeRepository.findByMemberIdAndProductId(memberId, productId)
        if (existingLike != null) {
            return existingLike
        }

        val like = Like.create(memberId, productId)
        return likeRepository.save(like)
    }

    fun unlike(memberId: Long, productId: Long) {
        val like = likeRepository.findByMemberIdAndProductId(memberId, productId)
        like?.let {
            likeRepository.deleteById(it.id!!)
        }
    }

    fun isLikedByUser(memberId: Long, productId: Long): Boolean {
        return likeRepository.existsByMemberIdAndProductId(memberId, productId)
    }

    fun findLikesByMember(memberId: Long): List<Like> {
        return likeRepository.findAllByMemberId(memberId)
    }

    fun countLikesByProduct(productId: Long): Int {
        return likeRepository.findAllByProductId(productId).size
    }
}
