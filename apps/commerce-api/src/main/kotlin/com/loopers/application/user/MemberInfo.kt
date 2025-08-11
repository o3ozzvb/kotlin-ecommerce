package com.loopers.application.user

import com.loopers.domain.member.Member

data class MemberInfo(
    val userId: String,
    val name: String,
    val gender: Member.Gender,
    val birthDay: String,
    val email: String,
) {
    companion object {
        fun from(entity: Member): MemberInfo {
            return MemberInfo(
                userId = entity.memberId,
                name = entity.name,
                gender = entity.gender,
                birthDay = entity.birthday,
                email = entity.email,
            )
        }
    }
}
