package com.loopers.application.user

import com.loopers.domain.member.Member

data class UserInfo(
    val userId: String,
    val name: String,
    val gender: Member.Gender,
    val birthDay: String,
    val email: String,
) {
    companion object {
        fun from(entity: Member): UserInfo {
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
