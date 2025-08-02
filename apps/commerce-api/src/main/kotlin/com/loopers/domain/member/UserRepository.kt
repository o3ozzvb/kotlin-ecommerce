package com.loopers.domain.member

import java.time.LocalDate

interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findByUserId(userId: String): User?
    fun findByEmail(email: String): User?
    fun existsByUserId(userId: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun findAllByStatus(status: User.UserStatus): List<User>
    fun findByBirthdayBetween(startDate: LocalDate, endDate: LocalDate): List<User>
    fun countByStatus(status: User.UserStatus): Long
    fun deleteById(id: Long)
}
