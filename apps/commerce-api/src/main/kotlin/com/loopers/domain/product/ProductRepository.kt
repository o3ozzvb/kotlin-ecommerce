package com.loopers.domain.product

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun find(id: Long): Product?
    fun findAll(): List<Product>
    fun findAll(pageable: Pageable): Page<Product>
}
