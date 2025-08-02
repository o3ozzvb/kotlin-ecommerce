package com.loopers.infrastructure.persistence.brand

import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandEntity, Long> {
    fun findByName(name: String): BrandEntity?
    fun existsByName(name: String): Boolean
}
