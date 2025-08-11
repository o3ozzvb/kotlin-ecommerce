package com.loopers.domain.member

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType

class MemberCommand {
    data class Create(
        val userId: String,
        val name: String,
        val gender: Member.Gender,
        val birthday: String,
        val email: String,
    ) {
        init {
            require(userId.isNotBlank()) { "userId cannot be blank" }
            require(name.isNotBlank() || Regex("^[A-Za-z0-9]{1,10}\$").matches(name)) {
                throw CoreException(ErrorType.BAD_REQUEST)
            }
            require(birthday.isNotBlank()) { "birthday cannot be blank" }
            require(email.isNotBlank()) { "email cannot be blank" }
        }
    }

    data class Update(
        val userId: String,
        val name: String? = null,
        val birthday: String? = null,
        val email: String? = null,
    ) {
        init {
            require(userId.isNotBlank()) { "userId cannot be blank" }
            name?.let { require(it.isNotBlank()) { "name cannot be blank" } }
            birthday?.let { require(it.isNotBlank()) { "birthday cannot be blank" } }
            email?.let { require(it.isNotBlank()) { "email cannot be blank" } }
        }
    }
}
