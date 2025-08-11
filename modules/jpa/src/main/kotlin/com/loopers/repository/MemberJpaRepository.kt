package com.loopers.repository

import com.loopers.domain.MemberEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun findByMemberId(memberId: String): MemberEntity?
    fun findByEmail(email: String): MemberEntity?
    fun existsByMemberId(memberId: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAllByStatus(status: MemberEntity.MemberStatus): List<MemberEntity>
    fun findAllByGender(gender: MemberEntity.Gender): List<MemberEntity>
    fun countByStatus(status: MemberEntity.MemberStatus): Long
}
