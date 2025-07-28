package com.loopers.infrastructure.user

import com.loopers.domain.member.MemberCriteria
import com.loopers.domain.member.Member
import com.loopers.domain.member.MemberRepository
import org.springframework.stereotype.Component

@Component
class MemberCoreRepository(
    private val userEntityJpaRepository: UserEntityJpaRepository,
) : MemberRepository {
    override fun save(user: Member): Member {
        return userEntityJpaRepository.save(user)
    }

    override fun find(userId: String): Member? {
        return userEntityJpaRepository.findByUserId(userId)
    }

    override fun find(criteria: MemberCriteria): List<Member> {
        /*
        val query = queryUnderstandingEngine.understand(criteria)

        userEntityJpaRepository.findAll().filter { user ->
            criteria.matches(user)
        }
         */
        return emptyList()
    }
}
