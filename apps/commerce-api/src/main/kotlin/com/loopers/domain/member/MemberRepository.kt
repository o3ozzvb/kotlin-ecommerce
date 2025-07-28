package com.loopers.domain.member

interface MemberRepository {
    fun save(user: Member): Member
    fun find(userId: String): Member?
    fun find(criteria: MemberCriteria): List<Member>
}
