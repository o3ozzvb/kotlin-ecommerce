package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductEntity, Long> {
    fun findByBrandId(brandId: Long, pageable: Pageable): Page<ProductEntity>
}
