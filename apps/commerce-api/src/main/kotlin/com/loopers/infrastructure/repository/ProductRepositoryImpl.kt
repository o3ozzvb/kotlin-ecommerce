package com.loopers.infrastructure.repository

import com.loopers.infrastructure.entity.ProductEntity
import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductData
import com.loopers.domain.product.ProductRepository
import com.loopers.infrastructure.ProductMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
            existingEntity.likeCount = productData.likeCount
            existingEntity
        } else {
            ProductEntity(
                name = productData.name,
                brandId = productData.brandId,
                inventoryId = productData.inventoryId,
                price = productData.price,
                likeCount = productData.likeCount,
            )
        }
        val savedEntity = productJpaRepository.save(entity)
        return ProductData(
            id = savedEntity.id,
            name = savedEntity.name,
            brandId = savedEntity.brandId,
            inventoryId = savedEntity.inventoryId,
            price = savedEntity.price,
            likeCount = savedEntity.likeCount,
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
                    likeCount = entity.likeCount,
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
        val sortedPageable = createSortedPageable(sortBy, pageable)
        val page = if (brandId != null) {
            productJpaRepository.findByBrandId(brandId, sortedPageable)
        } else {
            productJpaRepository.findAll(sortedPageable)
        }

        return page.map { entity ->
            ProductData(
                id = entity.id,
                name = entity.name,
                brandId = entity.brandId,
                inventoryId = entity.inventoryId,
                price = entity.price,
                likeCount = entity.likeCount,
            )
        }
    }

    private fun createSortedPageable(sortBy: String, pageable: Pageable): Pageable {
        val sort = when (sortBy) {
            "price_asc" -> Sort.by("price").ascending()
            "price_desc" -> Sort.by("price").descending()
            "name_asc" -> Sort.by("name").ascending()
            "name_desc" -> Sort.by("name").descending()
            "like_asc" -> Sort.by("likeCount").ascending()
            "like_desc" -> Sort.by("likeCount").descending()
            else -> Sort.by("id").ascending()
        }
        return PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)
    }
}
