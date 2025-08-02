package com.loopers.domain.member

import java.time.LocalDateTime

data class Member(
    val id: Long? = null,
    val memberId: String,
    val name: String,
    val birthday: String,
    val gender: Gender,
    val email: String,
    val status: MemberStatus = MemberStatus.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    enum class Gender { M, F }

    enum class MemberStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        WITHDRAWN,
    }

    companion object {
        fun create(command: MemberCommand.Create): Member {
            return Member(
                memberId = command.userId,
                name = command.name,
                birthday = command.birthday,
                gender = command.gender,
                email = command.email,
            )
        }
    }

    fun update(command: MemberCommand.Update): Member {
        return copy(
            name = command.name ?: this.name,
            birthday = command.birthday ?: this.birthday,
            email = command.email ?: this.email,
            updatedAt = LocalDateTime.now(),
        )
    }

    fun activate(): Member {
        return copy(status = MemberStatus.ACTIVE, updatedAt = LocalDateTime.now())
    }

    fun deactivate(): Member {
        return copy(status = MemberStatus.INACTIVE, updatedAt = LocalDateTime.now())
    }

    fun suspend(): Member {
        return copy(status = MemberStatus.SUSPENDED, updatedAt = LocalDateTime.now())
    }

    fun withdraw(): Member {
        return copy(status = MemberStatus.WITHDRAWN, updatedAt = LocalDateTime.now())
    }

    fun isActive(): Boolean = status == MemberStatus.ACTIVE

    fun isMale(): Boolean = gender == Gender.M

    fun isFemale(): Boolean = gender == Gender.F

    fun canPerformAction(): Boolean = status == MemberStatus.ACTIVE
}
