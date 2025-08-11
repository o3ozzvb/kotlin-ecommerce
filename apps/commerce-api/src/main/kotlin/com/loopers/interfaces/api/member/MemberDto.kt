package com.loopers.interfaces.api.member

import com.loopers.application.user.MemberInfo
import com.loopers.domain.member.MemberCommand
import com.loopers.domain.member.Member

<<<<<<<< HEAD:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberDto.kt
class MemberDto {
========
class MemberV1Dto {
>>>>>>>> origin/feature/round-3-like:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberV1Dto.kt
    class Request {
        data class SignUp(
            val userId: String,
            val name: String,
            val gender: Gender,
            val birthDay: String,
            val email: String,
        ) {
            fun toCommand(): MemberCommand.Create {
                return MemberCommand.Create(
                    userId = userId,
                    name = name,
                    gender = when (gender) {
                        Gender.M -> Member.Gender.M
                        Gender.F -> Member.Gender.F
                    },
                    birthday = birthDay,
                    email = email,
                )
            }
        }
    }

    class Response {
        data class UserResponse(
            val userId: String,
            val name: String,
            val gender: Gender,
            val birthDay: String,
            val email: String,
        ) {
            companion object {
                fun from(memberInfo: MemberInfo): UserResponse {
                    return UserResponse(
                        userId = memberInfo.userId,
                        name = memberInfo.name,
                        gender = when (memberInfo.gender) {
                            Member.Gender.M -> Gender.M
                            Member.Gender.F -> Gender.F
                        },
                        birthDay = memberInfo.birthDay,
                        email = memberInfo.email,
                    )
                }
            }
        }
    }

    enum class Gender {
        M,
        F,
    }
}
