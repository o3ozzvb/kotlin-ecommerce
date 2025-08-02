package com.loopers.infrastructure.user

import com.loopers.infrastructure.persistence.member.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEntityJpaRepository : JpaRepository<MemberEntity, Long> {
    fun findByMemberId(memberId: String): MemberEntity?
}
