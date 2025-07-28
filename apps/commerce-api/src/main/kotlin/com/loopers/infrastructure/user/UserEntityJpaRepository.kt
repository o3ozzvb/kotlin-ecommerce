package com.loopers.infrastructure.user

import com.loopers.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityJpaRepository : JpaRepository<Member, Long> {
    fun findByUserId(userId: String): Member?
}
