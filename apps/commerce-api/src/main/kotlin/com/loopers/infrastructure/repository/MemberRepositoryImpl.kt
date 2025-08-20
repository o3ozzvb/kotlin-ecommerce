package com.loopers.infrastructure.repository

import com.loopers.domain.member.Member
import com.loopers.domain.member.MemberCriteria
import com.loopers.domain.member.MemberRepository
import com.loopers.infrastructure.entity.MemberEntity
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberRepository {

    override fun save(member: Member): Member {
        val entity = if (member.id != null) {
            val existingEntity = memberJpaRepository.findById(member.id).orElse(MemberEntity())
            existingEntity.memberId = member.memberId
            existingEntity.name = member.name
            existingEntity.birthday = member.birthday
            existingEntity.gender = MemberEntity.Gender.valueOf(member.gender.name)
            existingEntity.email = member.email
            existingEntity.status = MemberEntity.MemberStatus.valueOf(member.status.name)
            existingEntity
        } else {
            MemberEntity(
                memberId = member.memberId,
                name = member.name,
                birthday = member.birthday,
                gender = MemberEntity.Gender.valueOf(member.gender.name),
                email = member.email,
                status = MemberEntity.MemberStatus.valueOf(member.status.name),
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
        return memberJpaRepository.findAllByStatus(MemberEntity.MemberStatus.valueOf(status.name))
            .map { it.toDomain() }
    }

    override fun findAllByGender(gender: Member.Gender): List<Member> {
        return memberJpaRepository.findAllByGender(MemberEntity.Gender.valueOf(gender.name))
            .map { it.toDomain() }
    }

    override fun countByStatus(status: Member.MemberStatus): Long {
        return memberJpaRepository.countByStatus(MemberEntity.MemberStatus.valueOf(status.name))
    }

    override fun deleteById(id: Long) {
        memberJpaRepository.deleteById(id)
    }

    override fun find(criteria: MemberCriteria): List<Member> {
        return memberJpaRepository.findAll().map { it.toDomain() }
    }

    private fun MemberEntity.toDomain(): Member = Member(
        id = this.id,
        memberId = this.memberId,
        name = this.name,
        birthday = this.birthday,
        gender = Member.Gender.valueOf(this.gender.name),
        email = this.email,
        status = Member.MemberStatus.valueOf(this.status.name),
        createdAt = this.createdAt.toLocalDateTime(),
        updatedAt = this.updatedAt.toLocalDateTime(),
    )
}
