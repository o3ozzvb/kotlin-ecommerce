package com.loopers.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "brand")
data class BrandEntity(
    @Column(nullable = false, length = 100)
    val name: String,

    @Column(length = 500)
    val description: String? = null,
) : BaseEntity()
