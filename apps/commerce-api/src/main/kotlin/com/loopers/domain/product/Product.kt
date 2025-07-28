package com.loopers.domain.product

import com.loopers.domain.BaseEntity
import com.loopers.domain.brand.Brand
import com.loopers.domain.inventory.Inventory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
class Product(
    name: String,
    brand: Brand,
    inventory: Inventory,
    price: BigDecimal,
) : BaseEntity() {
    @Column(name = "name", nullable = false)
    var name: String = name
        protected set

    @Column(name = "brand_id", nullable = false)
    var brand_id: Long = brand.id
        protected set

    @Column(name = "inventory_id", nullable = false)
    var inventory_id: Long = inventory.id
        protected set

    @Column(name = "price", nullable = false)
    var price: BigDecimal = price
        protected set
}
