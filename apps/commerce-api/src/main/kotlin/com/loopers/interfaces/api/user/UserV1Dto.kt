package com.loopers.interfaces.api.user

import com.loopers.application.user.UserInfo
import com.loopers.domain.user.UserCommand
import com.loopers.domain.user.UserEntity

class UserV1Dto {
    class Request {
        data class SignUp(
            val userId: String,
            val name: String,
            val gender: Gender,
            val birthDay: String,
            val email: String,
        ) {
            fun toCommand(): UserCommand.Create {
                return UserCommand.Create(
                    userId = userId,
                    name = name,
                    gender = when (gender) {
                        Gender.M -> UserEntity.Gender.M
                        Gender.F -> UserEntity.Gender.F
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
                            UserEntity.Gender.M -> Gender.M
                            UserEntity.Gender.F -> Gender.F
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
