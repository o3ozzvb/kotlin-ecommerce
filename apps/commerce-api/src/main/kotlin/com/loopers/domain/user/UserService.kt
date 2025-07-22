package com.loopers.domain.user

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserService(
    private val userRepository: UserRepository,
//    private val validator: UserValidator,
    ) {

    @Transactional
    fun create(command: UserCommand.Create): UserEntity {
//        validator.validate(command)
        val user = UserEntity(command)
        userRepository.save(user)
        return user
    }

    @Transactional
    fun update(command: UserCommand.Update): UserEntity {
        val user = userRepository.find(command.userId)
            ?: throw CoreException(ErrorType.NOT_FOUND, "해당 사용자를 찾을 수 없습니다")
        return user
    }

    fun find(userId: String): UserEntity? {
        return userRepository.find(userId)
    }
}
