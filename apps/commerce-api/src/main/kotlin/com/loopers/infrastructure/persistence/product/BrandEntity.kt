package com.loopers.infrastructure.persistence.product

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "brand")
class BrandEntity() : BaseEntity() {

    @Column(nullable = false, length = 100)
    var name: String = ""

    @Column(length = 500)
    var description: String? = null

    constructor(
        name: String,
        description: String? = null,
    ) : this() {
        this.name = name
        this.description = description
    }
}
