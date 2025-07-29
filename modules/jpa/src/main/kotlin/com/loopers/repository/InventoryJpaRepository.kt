package com.loopers.repository

import com.loopers.domain.InventoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface InventoryJpaRepository : JpaRepository<InventoryEntity, Long>
