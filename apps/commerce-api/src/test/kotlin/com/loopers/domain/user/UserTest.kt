package com.loopers.domain.user

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import com.loopers.domain.fixture.UserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.provider.CsvSource

class UserTest {

    @DisplayName("회원가입 할 때, 실패 테스트")
    @Nested
    inner class CreateFail {
        @DisplayName("ID 가 `영문 및 숫자 10자 이내` 형식에 맞지 않으면, User 객체 생성에 실패한다")
        @ParameterizedTest
        @ValueSource(strings = ["아이디", "idisoverthanten", "idwith!@#"])
        fun createMember_whenIDFormatIsNotValid(invalidUserId: String) {
            // act & assert
            assertThrows<IllegalArgumentException> {
                UserFixture.valid(userId = invalidUserId)
            }
        }

        @DisplayName("이메일이 `xx@yy.zz` 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @ParameterizedTest
        @ValueSource(strings = ["email", "email@loopers", "@loopers.com"])
        fun createMember_whenEmailFormatIsNotValid(invalidEmail: String) {
            // act & assert
            assertThrows<IllegalArgumentException> {
                UserFixture.valid(email = invalidEmail)
            }
        }

        @DisplayName("생년월일이 `yyyy-MM-dd` 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @ParameterizedTest
        @ValueSource(strings = ["1999/1/11", "19990111", "99-01-11", "abcd-ef-gh"])
        fun createMember_whenBirthdayFormatIsNotValid(invalidBirthday: String) {
            // act & assert
            assertThrows<IllegalArgumentException> {
                UserFixture.valid(birthday = invalidBirthday)
            }
        }
    }

    @DisplayName("회원가입 할 때, 성공 테스트")
    @Nested
    inner class CreateSuccess {
        @DisplayName("ID, Email, 생년월일이 형식에 맞으면, User 객체 생성에 성공한다")
        @ParameterizedTest(name = "#{index} → userId={0}, email={1}, birthday={2}")
        @CsvSource(
            "a, a@b.c, 1970-01-01",
            "id12345678, l@o.op, 2099-12-31",
        )
        fun createMember(
            userId: String,
            email: String,
            birthday: String,
        ) {
            // act
            val user = User.of(userId, email, birthday)

            // assert
            assertThat(user.userId).isEqualTo(userId)
            assertThat(user.email).isEqualTo(email)
            assertThat(user.birthday).isEqualTo(birthday)
        }
    }
}
