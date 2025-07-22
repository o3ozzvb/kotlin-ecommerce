package com.loopers.interfaces.api.user

import com.loopers.application.user.UserFacade
import com.loopers.interfaces.api.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * - [ ]  회원 가입이 성공할 경우, 생성된 유저 정보를 응답으로 반환한다.
 * - [ ]  회원 가입 시에 성별이 없을 경우, `400 Bad Request` 응답을 반환한다.
 */
@RestController
@RequestMapping("/api/v1/users")
class UserV1ApiController(
    private val userFacade: UserFacade,
) : UserV1ApiSpec {

    @PostMapping
    fun signUp(
        @RequestBody request: UserV1Dto.Request.SignUp,
    ): ApiResponse<UserV1Dto.Response.UserResponse> {
        /*
        return UserV1Dto.Response.UserResponse(
            userId = "oeunkyoung",
            name = "응경",
            gender = "F",
            birth = "2000-11-12",
            email = "oeunkyoung@loopers.com",
        ).let { ApiResponse.success(it)
         */

        return userFacade.signUp(request.toCommand())
            .let { UserV1Dto.Response.UserResponse.from(it) }
            .let { ApiResponse.success(it) }
    }

    @GetMapping("/me")
    fun me(
        request: HttpServletRequest,
    ): ApiResponse<UserV1Dto.Response.UserResponse> {
        val userId = request.getHeader("X-USER-ID")
            ?: throw IllegalArgumentException("X_USER_ID 헤더가 필요합니다.")

        return userFacade.me(userId)
            .let { UserV1Dto.Response.UserResponse.from(it) }
            .let { ApiResponse.success(it) }
    }
}
