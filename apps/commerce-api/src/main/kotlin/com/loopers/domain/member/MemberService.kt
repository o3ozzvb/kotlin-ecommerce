package com.loopers.domain.member

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MemberService(
    private val memberRepository: MemberRepository,
//    private val validator: UserValidator,
    ) {

    @Transactional
    fun create(command: MemberCommand.Create): Member {
//        validator.validate(command)
        val user = Member.create(command)
        memberRepository.save(user)
        return user
    }

    @Transactional
    fun update(command: MemberCommand.Update): Member {
        val user = memberRepository.findByMemberId(command.userId)
            ?: throw CoreException(ErrorType.NOT_FOUND, "해당 사용자를 찾을 수 없습니다")
        val updatedUser = user.update(command)
        return memberRepository.save(updatedUser)
    }

    fun find(userId: String): Member? {
        return memberRepository.findByMemberId(userId)
    }
}
