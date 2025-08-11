package com.loopers.infrastructure.repository

import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductData
import com.loopers.domain.product.ProductRepository
import com.loopers.domain.ProductEntity
import com.loopers.repository.ProductJpaRepository
import com.loopers.infrastructure.ProductMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
    private val productMapper: ProductMapper,
) : ProductRepository {

    override fun save(productData: ProductData): ProductData {
        val entity = if (productData.id != 0L) {
            val existingEntity = productJpaRepository.findById(productData.id).orElse(ProductEntity())
            existingEntity.name = productData.name
            existingEntity.brandId = productData.brandId
            existingEntity.inventoryId = productData.inventoryId
            existingEntity.price = productData.price
            existingEntity
        } else {
            ProductEntity(
                name = productData.name,
                brandId = productData.brandId,
                inventoryId = productData.inventoryId,
                price = productData.price,
            )
        }
        val savedEntity = productJpaRepository.save(entity)
        return ProductData(
            id = savedEntity.id,
            name = savedEntity.name,
            brandId = savedEntity.brandId,
            inventoryId = savedEntity.inventoryId,
            price = savedEntity.price,
        )
    }

    override fun findById(id: Long): ProductData? {
        return productJpaRepository.findById(id)
            .map { entity ->
                ProductData(
                    id = entity.id,
                    name = entity.name,
                    brandId = entity.brandId,
                    inventoryId = entity.inventoryId,
                    price = entity.price,
                )
            }
            .orElse(null)
    }

    override fun deleteById(id: Long) {
        productJpaRepository.deleteById(id)
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
        val page = if (brandId != null) {
            productJpaRepository.findByBrandId(brandId, pageable)
        } else {
            productJpaRepository.findAll(pageable)
        }

        val products = page.content.map { entity -> productMapper.toProduct(entity) }
        return PageImpl(products, pageable, page.totalElements)
    }

    override fun findProductsData(brandId: Long?, sortBy: String, pageable: Pageable): Page<ProductData> {
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
