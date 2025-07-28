package com.loopers.application.user

import com.loopers.domain.member.MemberCommand
import com.loopers.domain.member.MemberService
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserFacade(
    private val memberService: MemberService,
) {
    @Transactional
    fun signUp(command: MemberCommand.Create): UserInfo {
        memberService.find(command.userId)?.let {
            throw CoreException(ErrorType.BAD_REQUEST, "이미 존재하는 사용자입니다. ${command.userId}")
        }

        return memberService.create(command)
            .let { UserInfo.from(it) }
    }

    @Transactional
    fun me(userId: String): UserInfo {
        return memberService.find(userId)
            ?.let { UserInfo.from(it) }
            ?: throw CoreException(ErrorType.BAD_REQUEST, "잘못된 접근입니다. 해당 사용자를 찾을 수 없습니다: $userId")
    }
}
