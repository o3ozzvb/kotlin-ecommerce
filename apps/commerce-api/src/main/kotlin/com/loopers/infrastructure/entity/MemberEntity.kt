package com.loopers.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.Table

@Entity
@Table(name = "member")
class MemberEntity() : BaseEntity() {

    enum class Gender { M, F }
    enum class MemberStatus { ACTIVE, INACTIVE, SUSPENDED }

    @Column(name = "member_id", nullable = false, unique = true)
    var memberId: String = ""

    @Column(name = "name", nullable = false)
    var name: String = ""

    @Column(name = "birthday", nullable = false)
    var birthday: String = ""

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    var gender: Gender = Gender.M

    @Column(name = "email", nullable = false)
    var email: String = ""

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: MemberStatus = MemberStatus.ACTIVE

    constructor(
        memberId: String,
        name: String,
        birthday: String,
        gender: Gender,
        email: String,
        status: MemberStatus = MemberStatus.ACTIVE,
    ) : this() {
        this.memberId = memberId
        this.name = name
        this.birthday = birthday
        this.gender = gender
        this.email = email
        this.status = status
    }
}
