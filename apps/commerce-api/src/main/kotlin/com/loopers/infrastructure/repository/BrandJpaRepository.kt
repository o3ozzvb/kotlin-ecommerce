package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandEntity, Long> {
    fun findByName(name: String): BrandEntity?
    fun existsByName(name: String): Boolean
}
