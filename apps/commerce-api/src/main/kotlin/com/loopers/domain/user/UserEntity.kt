package com.loopers.domain.user

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class UserEntity(
    userId: String,
    name: String,
    birthday: String,
    gender: Gender,
    email: String,
) : BaseEntity() {
    companion object {
        fun from(command: UserCommand.Create): UserEntity {
            return UserEntity(
                userId = command.userId,
                name = command.name,
                birthday = command.birthday,
                gender = command.gender,
                email = command.email,
            )
        }
    }

    enum class Gender {
        M,
        F,
    }

    constructor(command: UserCommand.Create) : this(
        userId = command.userId,
        name = command.name,
        birthday = command.birthday,
        gender = command.gender,
        email = command.email,
    )

    @Column(name = "user_id", nullable = false)
    val userId: String = userId

    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "birthday", nullable = false)
    var birthday: String = birthday
        protected set

    @Column(name = "gender", nullable = false)
    var gender: Gender = gender

    @Column(name = "email", nullable = false)
    var email: String = email
        protected set

    fun update(command: UserCommand.Update) {
        command.name?.let { this.name = it }
        command.birthday?.let { this.birthday = it }
        command.email?.let { this.email = it }
    }
}
