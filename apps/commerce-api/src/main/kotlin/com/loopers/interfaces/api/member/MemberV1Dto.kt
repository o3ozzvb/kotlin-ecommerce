package com.loopers.interfaces.api.member

import com.loopers.application.user.UserInfo
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
                fun from(userInfo: UserInfo): UserResponse {
                    return UserResponse(
                        userId = userInfo.userId,
                        name = userInfo.name,
                        gender = when (userInfo.gender) {
                            Member.Gender.M -> Gender.M
                            Member.Gender.F -> Gender.F
                        },
                        birthDay = userInfo.birthDay,
                        email = userInfo.email,
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
