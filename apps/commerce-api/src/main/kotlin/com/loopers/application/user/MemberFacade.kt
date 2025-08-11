package com.loopers.application.user

import com.loopers.domain.member.MemberCommand
import com.loopers.domain.member.MemberService
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberFacade(
    private val memberService: MemberService,
) {
    @Transactional
    fun signUp(command: MemberCommand.Create): MemberInfo {
        memberService.find(command.userId)?.let {
            throw CoreException(ErrorType.BAD_REQUEST, "이미 존재하는 사용자입니다. ${command.userId}")
        }

        return memberService.create(command)
            .let { MemberInfo.from(it) }
    }

    @Transactional
    fun me(userId: String): MemberInfo {
        return memberService.find(userId)
            ?.let { MemberInfo.from(it) }
            ?: throw CoreException(ErrorType.BAD_REQUEST, "잘못된 접근입니다. 해당 사용자를 찾을 수 없습니다: $userId")
    }
}
