package com.loopers.domain.brand

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

data class Brand(
    val id: Long,
    val name: String,
    val description: String,
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
    }
}
