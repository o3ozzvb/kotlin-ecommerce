package com.loopers.repository

import com.loopers.domain.BrandEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandEntity, Long>
