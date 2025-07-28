package com.loopers.domain.member

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class Member(
    memberId: String,
    name: String,
    birthday: String,
    gender: Gender,
    email: String,
) : BaseEntity() {
    companion object {
        fun from(command: MemberCommand.Create): Member {
            return Member(
                memberId = command.userId,
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

    constructor(command: MemberCommand.Create) : this(
        memberId = command.userId,
        name = command.name,
        birthday = command.birthday,
        gender = command.gender,
        email = command.email,
    )

    @Column(name = "member_id", nullable = false)
    val memberId: String = memberId

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

    fun update(command: MemberCommand.Update) {
        command.name?.let { this.name = it }
        command.birthday?.let { this.birthday = it }
        command.email?.let { this.email = it }
    }
}
