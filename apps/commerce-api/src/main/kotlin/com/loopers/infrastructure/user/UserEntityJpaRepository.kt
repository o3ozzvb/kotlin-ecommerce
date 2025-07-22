package com.loopers.infrastructure.user

import com.loopers.domain.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByUserId(userId: String): UserEntity?
}
