package com.loopers.domain.brand

interface BrandRepository {
    fun save(brand: Brand): Brand
    fun findById(id: Long): Brand?
    fun findByName(name: String): Brand?
    fun findAll(): List<Brand>
    fun existsByName(name: String): Boolean
    fun deleteById(id: Long)
}
