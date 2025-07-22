package com.loopers.infrastructure.user

import com.loopers.domain.user.UserCriteria
import com.loopers.domain.user.UserEntity
import com.loopers.domain.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserCoreRepository(
    private val userEntityJpaRepository: UserEntityJpaRepository,
) : UserRepository {
    override fun save(user: UserEntity): UserEntity {
        return userEntityJpaRepository.save(user)
    }

    override fun find(userId: String): UserEntity? {
        return userEntityJpaRepository.findByUserId(userId)
    }

    override fun find(criteria: UserCriteria): List<UserEntity> {
        /*
        val query = queryUnderstandingEngine.understand(criteria)

        userEntityJpaRepository.findAll().filter { user ->
            criteria.matches(user)
        }
         */
        return emptyList()
    }
}
