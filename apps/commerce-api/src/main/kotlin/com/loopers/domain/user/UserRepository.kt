package com.loopers.domain.user

interface UserRepository {
    fun save(user: UserEntity): UserEntity
    fun find(userId: String): UserEntity?
    fun find(criteria: UserCriteria): List<UserEntity>
}
