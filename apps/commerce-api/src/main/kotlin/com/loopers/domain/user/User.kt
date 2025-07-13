package com.loopers.domain.user

import com.fasterxml.jackson.annotation.JsonFormat
import com.loopers.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "users")
data class User(
    val userId: String,
    val email: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val birthday: LocalDate,
    ) : BaseEntity() {
        companion object {
            private val ID_REGEX = Regex("^[A-Za-z0-9]{1,10}$")
            private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{1,}\$")
            private val DATE_FORMAT = DateTimeFormatter.ISO_DATE // yyyy-MM-dd

            fun of(userId: String, email: String, birthday: String): User {
                require(userId.matches(ID_REGEX)) {
                    "ID 형식이 올바르지 않습니다. (영문 또는 숫자 10자 이내): $userId"
                }

                require(email.matches(EMAIL_REGEX)) {
                    "Email 형식이 올바르지 않습니다. (xx@yy.zz): $email"
                }

                val parsedBirthday = kotlin.runCatching { LocalDate.parse(birthday, DATE_FORMAT) }
                    .getOrElse { throw IllegalArgumentException("생일 형식이 올바르지 않습니다. (yyyy-MM-dd)") }

                return User(userId, email, parsedBirthday)
            }
        }
    }
