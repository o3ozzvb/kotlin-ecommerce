package com.loopers.infrastructure.persistence.member

import com.loopers.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {

    fun findByMemberId(memberId: String): MemberEntity?

    fun findByEmail(email: String): MemberEntity?

    fun existsByMemberId(memberId: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun findAllByStatus(status: Member.MemberStatus): List<MemberEntity>

    fun findAllByGender(gender: Member.Gender): List<MemberEntity>

    @Query("SELECT m FROM MemberEntity m WHERE m.status = :status AND m.gender = :gender")
    fun findByStatusAndGender(status: Member.MemberStatus, gender: Member.Gender): List<MemberEntity>

    @Query("SELECT COUNT(m) FROM MemberEntity m WHERE m.status = :status")
    fun countByStatus(status: Member.MemberStatus): Long
}
