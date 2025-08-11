package com.loopers.infrastructure.repository

import com.loopers.domain.BrandEntity
import com.loopers.domain.brand.Brand
import com.loopers.domain.brand.BrandRepository
import com.loopers.repository.BrandJpaRepository
import org.springframework.stereotype.Repository

@Repository
class BrandRepositoryImpl(
    private val brandJpaRepository: BrandJpaRepository,
) : BrandRepository {

    override fun save(brand: Brand): Brand {
        val entity = if (brand.id != 0L) {
            val existingEntity = brandJpaRepository.findById(brand.id).orElse(BrandEntity())
            existingEntity.name = brand.name
            existingEntity.description = brand.description
            existingEntity
        } else {
            BrandEntity(
                name = brand.name,
                description = brand.description,
            )
        }
        val savedEntity = brandJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): Brand? {
        return brandJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByName(name: String): Brand? {
        return brandJpaRepository.findByName(name)?.toDomain()
    }

    override fun findAll(): List<Brand> {
        return brandJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun existsByName(name: String): Boolean {
        return brandJpaRepository.existsByName(name)
    }

    override fun deleteById(id: Long) {
        brandJpaRepository.deleteById(id)
    }

    private fun BrandEntity.toDomain(): Brand = Brand(
        id = this.id,
        name = this.name,
        description = this.description,
    )
}
