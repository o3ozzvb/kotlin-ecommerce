package com.loopers.interfaces.api.member

import com.loopers.application.user.MemberFacade
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
<<<<<<<< HEAD:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberController.kt
class MemberController(
    private val memberFacade: MemberFacade,
) : MemberSpec {

    @PostMapping
    fun signUp(
        @RequestBody request: MemberDto.Request.SignUp,
    ): ApiResponse<MemberDto.Response.UserResponse> {
========
class MemberV1ApiController(
    private val memberFacade: MemberFacade,
) : MemberV1ApiSpec {

    @PostMapping
    fun signUp(
        @RequestBody request: MemberV1Dto.Request.SignUp,
    ): ApiResponse<MemberV1Dto.Response.UserResponse> {
>>>>>>>> origin/feature/round-3-like:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberV1ApiController.kt
        /*
        return UserV1Dto.Response.UserResponse(
            userId = "oeunkyoung",
            name = "응경",
            gender = "F",
            birth = "2000-11-12",
            email = "oeunkyoung@loopers.com",
        ).let { ApiResponse.success(it)
         */

        return memberFacade.signUp(request.toCommand())
<<<<<<<< HEAD:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberController.kt
            .let { MemberDto.Response.UserResponse.from(it) }
========
            .let { MemberV1Dto.Response.UserResponse.from(it) }
>>>>>>>> origin/feature/round-3-like:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberV1ApiController.kt
            .let { ApiResponse.success(it) }
    }

    @GetMapping("/me")
    fun me(
        request: HttpServletRequest,
<<<<<<<< HEAD:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberController.kt
    ): ApiResponse<MemberDto.Response.UserResponse> {
========
    ): ApiResponse<MemberV1Dto.Response.UserResponse> {
>>>>>>>> origin/feature/round-3-like:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberV1ApiController.kt
        val userId = request.getHeader("X-USER-ID")
            ?: throw IllegalArgumentException("X_USER_ID 헤더가 필요합니다.")

        return memberFacade.me(userId)
<<<<<<<< HEAD:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberController.kt
            .let { MemberDto.Response.UserResponse.from(it) }
========
            .let { MemberV1Dto.Response.UserResponse.from(it) }
>>>>>>>> origin/feature/round-3-like:apps/commerce-api/src/main/kotlin/com/loopers/interfaces/api/member/MemberV1ApiController.kt
            .let { ApiResponse.success(it) }
    }
}
