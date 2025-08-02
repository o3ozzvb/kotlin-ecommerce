package com.loopers.infrastructure.product

import com.loopers.infrastructure.persistence.product.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandEntity, Long> {
    fun findByName(name: String): BrandEntity?
    fun existsByName(name: String): Boolean
}
