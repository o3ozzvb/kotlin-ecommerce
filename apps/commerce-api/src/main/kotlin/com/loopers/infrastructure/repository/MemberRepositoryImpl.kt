package com.loopers.infrastructure.repository

import com.loopers.domain.member.Member
import com.loopers.domain.member.MemberCriteria
import com.loopers.domain.member.MemberRepository
import com.loopers.infrastructure.persistence.member.MemberEntity
import com.loopers.infrastructure.persistence.member.MemberJpaRepository
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {

    override fun save(member: Member): Member {
        val entity = if (member.id != null) {
            // Update existing entity
            val existingEntity = memberJpaRepository.findById(member.id).orElse(MemberEntity())
            existingEntity.memberId = member.memberId
            existingEntity.name = member.name
            existingEntity.birthday = member.birthday
            existingEntity.gender = member.gender
            existingEntity.email = member.email
            existingEntity.status = member.status
            existingEntity
        } else {
            // Create new entity
            MemberEntity(
                memberId = member.memberId,
                name = member.name,
                birthday = member.birthday,
                gender = member.gender,
                email = member.email,
                status = member.status,
            )
        }
        val savedEntity = memberJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): Member? {
        return memberJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByMemberId(memberId: String): Member? {
        return memberJpaRepository.findByMemberId(memberId)?.toDomain()
    }

    override fun findByEmail(email: String): Member? {
        return memberJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun existsByMemberId(memberId: String): Boolean {
        return memberJpaRepository.existsByMemberId(memberId)
    }

    override fun existsByEmail(email: String): Boolean {
        return memberJpaRepository.existsByEmail(email)
    }

    override fun findAllByStatus(status: Member.MemberStatus): List<Member> {
        return memberJpaRepository.findAllByStatus(status)
            .map { it.toDomain() }
    }

    override fun findAllByGender(gender: Member.Gender): List<Member> {
        return memberJpaRepository.findAllByGender(gender)
            .map { it.toDomain() }
    }

    override fun countByStatus(status: Member.MemberStatus): Long {
        return memberJpaRepository.countByStatus(status)
    }

    override fun deleteById(id: Long) {
        memberJpaRepository.deleteById(id)
    }

    override fun find(criteria: MemberCriteria): List<Member> {
        // 간단한 구현 - 실제로는 criteria에 따른 복잡한 쿼리 필요
        return memberJpaRepository.findAll().map { it.toDomain() }
    }

    // Entity -> Domain 변환
    private fun MemberEntity.toDomain(): Member = Member(
        id = this.id,
        memberId = this.memberId,
        name = this.name,
        birthday = this.birthday,
        gender = this.gender,
        email = this.email,
        status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
