package com.loopers.infrastructure.persistence.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductJpaRepository : JpaRepository<ProductEntity, Long> {
    fun findByBrandId(brandId: Long): List<ProductEntity>
    fun findByBrandId(brandId: Long, pageable: Pageable): Page<ProductEntity>
    fun findByName(name: String): List<ProductEntity>
}
