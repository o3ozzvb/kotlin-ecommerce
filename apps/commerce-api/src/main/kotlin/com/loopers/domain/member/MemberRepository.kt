package com.loopers.domain.member

interface MemberRepository {
    fun save(member: Member): Member
    fun findById(id: Long): Member?
    fun findByMemberId(memberId: String): Member?
    fun findByEmail(email: String): Member?
    fun existsByMemberId(memberId: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAllByStatus(status: Member.MemberStatus): List<Member>
    fun findAllByGender(gender: Member.Gender): List<Member>
    fun countByStatus(status: Member.MemberStatus): Long
    fun deleteById(id: Long)
    fun find(criteria: MemberCriteria): List<Member>
}
