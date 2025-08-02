package com.loopers.infrastructure.product

import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductData
import com.loopers.domain.product.ProductRepository
import com.loopers.infrastructure.persistence.product.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val productMapper: ProductMapper,
) : ProductRepository {

    override fun save(product: Product): Product {
        val entity = if (product.id != 0L) {
            // Update existing product
            val existingEntity = productJpaRepository.findById(product.id).orElse(ProductEntity())
            existingEntity.name = product.name
            existingEntity.brandId = product.brand.id
            existingEntity.inventoryId = product.inventory.id
            existingEntity.price = product.price
            existingEntity
        } else {
            // Create new product
            ProductEntity(
                name = product.name,
                brandId = product.brand.id,
                inventoryId = product.inventory.id,
                price = product.price,
            )
        }
        val savedEntity = productJpaRepository.save(entity)
        return productMapper.toProduct(savedEntity)
    }

    fun find(id: Long): Product? {
        return productJpaRepository.findById(id)
            .map { productEntity ->
                productMapper.toProduct(productEntity)
            }
            .orElse(null)
    }

    fun findAll(): List<Product> {
        return productJpaRepository.findAll()
            .map { productMapper.toProduct(it) }
    }

    fun findAll(pageable: Pageable): Page<Product> {
        val page = productJpaRepository.findAll(pageable)
        val products = page.content.map { productMapper.toProduct(it) }
        return PageImpl(products, pageable, page.totalElements)
    }

    override fun findProducts(brandId: Long?, sortBy: String, pageable: Pageable): Page<Product> {
        // Use simple JPA repository methods to avoid KotlinJDSL executor AOP issues
        val page = if (brandId != null) {
            productJpaRepository.findByBrandId(brandId, pageable)
        } else {
            productJpaRepository.findAll(pageable)
        }

        val products = page.content.map { entity -> productMapper.toProduct(entity) }
        return PageImpl(products, pageable, page.totalElements)
    }

    override fun findProductsData(brandId: Long?, sortBy: String, pageable: Pageable): Page<ProductData> {
        // Use simple JPA repository methods to avoid KotlinJDSL executor AOP issues
        val page = if (brandId != null) {
            productJpaRepository.findByBrandId(brandId, pageable)
        } else {
            productJpaRepository.findAll(pageable)
        }

        val productData = page.content.map { entity ->
            ProductData(
                id = entity.id,
                name = entity.name,
                brandId = entity.brandId,
                inventoryId = entity.inventoryId,
                price = entity.price,
            )
        }
        return PageImpl(productData, pageable, page.totalElements)
    }
}
