package com.loopers.interfaces.api

import com.loopers.utils.DatabaseCleanUp
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
<<<<<<<< HEAD:apps/commerce-api/src/test/kotlin/com/loopers/interfaces/api/MemberApiE2ETest.kt
class MemberApiE2ETest @Autowired constructor(
========
class MemberV1ApiE2ETest @Autowired constructor(
>>>>>>>> origin/feature/round-3-like:apps/commerce-api/src/test/kotlin/com/loopers/interfaces/api/MemberV1ApiE2ETest.kt
    private val testRestTemplate: TestRestTemplate,
    // private val userJpaRepository: UserJpaRepository,
    private val databaseCleanUp: DatabaseCleanUp,
) {
    companion object {
        private val ENDPOINT_GET: (Long) -> String = { id: Long -> "/api/v1/users" }
    }

    @AfterEach
    fun tearDown() {
        databaseCleanUp.truncateAllTables()
    }

    @DisplayName("POST /api/v1/user")
    @Nested
    inner class Post {
        @DisplayName("회원 가입이 성공할 경우, 생성된 유저 정보를 응답으로 반환한다.")
        @Test
        fun returnUserInfo_whenSignUpSuccessful() {
            // arrange
            // val requestUrl = ENDPOINT

            // act

            // assert
        }
    }
}
