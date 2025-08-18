package com.loopers.domain.brand

import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: BrandRepository,
) {
    fun findById(id: Long): Brand {
        return brandRepository.findById(id)
            ?: throw IllegalStateException("Brand not found: $id")
    }

    fun findByName(name: String): Brand? {
        return brandRepository.findByName(name)
    }

    fun findAll(): List<Brand> {
        return brandRepository.findAll()
    }

    fun existsByName(name: String): Boolean {
        return brandRepository.existsByName(name)
    }

    fun save(brand: Brand): Brand {
        return brandRepository.save(brand)
    }

    fun deleteById(id: Long) {
        brandRepository.deleteById(id)
    }
}
