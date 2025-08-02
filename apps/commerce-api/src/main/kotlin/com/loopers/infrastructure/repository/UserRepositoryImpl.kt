package com.loopers.infrastructure.repository

import com.loopers.domain.member.User
import com.loopers.domain.member.UserRepository
import com.loopers.infrastructure.persistence.member.UserEntity
import com.loopers.infrastructure.persistence.member.UserJpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {

    override fun save(user: User): User {
        val entity = if (user.id != null) {
            // Update existing entity
            val existingEntity = userJpaRepository.findById(user.id).orElse(UserEntity())
            existingEntity.apply {
                this.userId = user.userId
                this.email = user.email
                this.birthday = user.birthday
                this.status = user.status
            }
        } else {
            // Create new entity
            UserEntity(
                userId = user.userId,
                email = user.email,
                birthday = user.birthday,
                status = user.status,
            )
        }
        val savedEntity = userJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByUserId(userId: String): User? {
        return userJpaRepository.findByUserId(userId)?.toDomain()
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun existsByUserId(userId: String): Boolean {
        return userJpaRepository.existsByUserId(userId)
    }

    override fun existsByEmail(email: String): Boolean {
        return userJpaRepository.existsByEmail(email)
    }

    override fun findAllByStatus(status: User.UserStatus): List<User> {
        return userJpaRepository.findAllByStatus(status)
            .map { it.toDomain() }
    }

    override fun findByBirthdayBetween(startDate: LocalDate, endDate: LocalDate): List<User> {
        return userJpaRepository.findByBirthdayBetween(startDate, endDate)
            .map { it.toDomain() }
    }

    override fun countByStatus(status: User.UserStatus): Long {
        return userJpaRepository.countByStatus(status)
    }

    override fun deleteById(id: Long) {
        userJpaRepository.deleteById(id)
    }

    // Entity -> Domain 변환
    private fun UserEntity.toDomain(): User = User(
        id = this.id,
        userId = this.userId,
        email = this.email,
        birthday = this.birthday,
        status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
