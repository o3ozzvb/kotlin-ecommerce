package com.loopers.domain.fixture

import com.loopers.domain.member.User

object MemberFixture {
    fun valid(
        userId: String = "lia123",
        email: String = "lia@loopers.com",
        birthday: String = "1999-01-11",
    ): User = User.of(userId, email, birthday)
}
