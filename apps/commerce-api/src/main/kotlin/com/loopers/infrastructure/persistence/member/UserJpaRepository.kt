package com.loopers.infrastructure.persistence.member

import com.loopers.domain.member.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface UserJpaRepository : JpaRepository<UserEntity, Long> {

    fun findByUserId(userId: String): UserEntity?

    fun findByEmail(email: String): UserEntity?

    fun existsByUserId(userId: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun findAllByStatus(status: User.UserStatus): List<UserEntity>

    @Query("SELECT u FROM UserEntity u WHERE u.birthday >= :startDate AND u.birthday <= :endDate")
    fun findByBirthdayBetween(startDate: LocalDate, endDate: LocalDate): List<UserEntity>

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.status = :status")
    fun countByStatus(status: User.UserStatus): Long
}
