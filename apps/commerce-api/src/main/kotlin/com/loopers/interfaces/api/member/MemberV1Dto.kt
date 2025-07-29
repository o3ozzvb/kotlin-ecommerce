package com.loopers.interfaces.api.member

import com.loopers.application.user.MemberInfo
import com.loopers.domain.member.MemberCommand
import com.loopers.domain.member.Member

class MemberV1Dto {
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
