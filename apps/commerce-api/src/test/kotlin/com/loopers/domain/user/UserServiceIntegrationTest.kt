package com.loopers.domain.user

import com.loopers.utils.DatabaseCleanUp
import org.junit.jupiter.api.AfterEach
import org.springframework.boot.test.context.SpringBootTest

/**
 * - [ ]  회원 가입시 User 저장이 수행된다. ( spy 검증 )
 * - [ ]  이미 가입된 ID 로 회원가입 시도 시, 실패한다.
 */
@SpringBootTest
class UserServiceIntegrationTest(
    private val userService: UserService,
//    private val userJpaRepository: UserJpaRepository,
    private val databaseCleanUp: DatabaseCleanUp,
) {
    @AfterEach
    fun tearDown() {
        databaseCleanUp.truncateAllTables()
    }
}
