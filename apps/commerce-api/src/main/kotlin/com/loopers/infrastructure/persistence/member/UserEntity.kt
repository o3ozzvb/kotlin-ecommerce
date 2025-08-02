package com.loopers.infrastructure.persistence.member

import com.fasterxml.jackson.annotation.JsonFormat
import com.loopers.domain.BaseEntity
import com.loopers.domain.member.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "user")
class UserEntity() : BaseEntity() {

    @Column(name = "user_id", nullable = false, unique = true)
    var userId: String = ""

    @Column(name = "email", nullable = false, unique = true)
    var email: String = ""

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday", nullable = false)
    var birthday: LocalDate = LocalDate.now()

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: User.UserStatus = User.UserStatus.ACTIVE

    constructor(
        userId: String,
        email: String,
        birthday: LocalDate,
        status: User.UserStatus = User.UserStatus.ACTIVE,
    ) : this() {
        this.userId = userId
        this.email = email
        this.birthday = birthday
        this.status = status
    }
}
