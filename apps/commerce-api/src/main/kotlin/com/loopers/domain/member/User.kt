package com.loopers.domain.member

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

data class User(
    val id: Long? = null,
    val userId: String,
    val email: String,
    val birthday: LocalDate,
    val status: UserStatus = UserStatus.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    enum class UserStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        WITHDRAWN,
    }

    companion object {
        private val ID_REGEX = Regex("^[A-Za-z0-9]{1,10}$")
        private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,}\$")
        private val DATE_FORMAT = DateTimeFormatter.ISO_DATE // yyyy-MM-dd

        fun create(userId: String, email: String, birthday: String): User {
            require(userId.matches(ID_REGEX)) {
                "ID 형식이 올바르지 않습니다. (영문 또는 숫자 10자 이내): $userId"
            }

            require(email.matches(EMAIL_REGEX)) {
                "Email 형식이 올바르지 않습니다. (xx@yy.zz): $email"
            }

            val parsedBirthday = kotlin.runCatching { LocalDate.parse(birthday, DATE_FORMAT) }
                .getOrElse { throw IllegalArgumentException("생일 형식이 올바르지 않습니다. (yyyy-MM-dd)") }

            return User(
                userId = userId,
                email = email,
                birthday = parsedBirthday,
            )
        }
    }

    fun activate(): User {
        return copy(status = UserStatus.ACTIVE, updatedAt = LocalDateTime.now())
    }

    fun deactivate(): User {
        return copy(status = UserStatus.INACTIVE, updatedAt = LocalDateTime.now())
    }

    fun suspend(): User {
        return copy(status = UserStatus.SUSPENDED, updatedAt = LocalDateTime.now())
    }

    fun withdraw(): User {
        return copy(status = UserStatus.WITHDRAWN, updatedAt = LocalDateTime.now())
    }

    fun isActive(): Boolean = status == UserStatus.ACTIVE

    fun getAge(): Int {
        return Period.between(birthday, LocalDate.now()).years
    }

    fun isAdult(): Boolean = getAge() >= 19

    fun isValidForService(): Boolean {
        return status == UserStatus.ACTIVE && isAdult()
    }

    fun getBirthdayFormatted(): String {
        return birthday.format(DATE_FORMAT)
    }
}
