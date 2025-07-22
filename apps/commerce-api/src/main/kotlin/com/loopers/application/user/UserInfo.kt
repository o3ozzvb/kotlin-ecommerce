package com.loopers.application.user

import com.loopers.domain.user.UserEntity

data class UserInfo(
    val userId: String,
    val name: String,
    val gender: UserEntity.Gender,
    val birthDay: String,
    val email: String,
) {
    companion object {
        fun from(entity: UserEntity): UserInfo {
            return UserInfo(
                userId = entity.userId,
                name = entity.name,
                gender = entity.gender,
                birthDay = entity.birthday,
                email = entity.email,
            )
        }
    }
}
