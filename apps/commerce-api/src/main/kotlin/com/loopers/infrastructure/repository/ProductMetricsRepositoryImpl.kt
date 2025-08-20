package com.loopers.infrastructure.repository

import com.loopers.domain.product.ProductMetrics
import com.loopers.domain.product.ProductMetricsRepository
import com.loopers.infrastructure.entity.ProductMetricsEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class ProductMetricsRepositoryImpl(
    private val productMetricsJpaRepository: ProductMetricsJpaRepository,
) : ProductMetricsRepository {

    override fun save(productMetrics: ProductMetrics): ProductMetrics {
        val entity = if (productMetrics.id != null) {
            val existingEntity = productMetricsJpaRepository.findById(productMetrics.id).orElse(ProductMetricsEntity())
            existingEntity.apply {
                this.productId = productMetrics.productId
                this.likeCount = productMetrics.likeCount
                this.salesCount = productMetrics.salesCount
                this.viewCount = productMetrics.viewCount
            }
        } else {
            ProductMetricsEntity(
                productId = productMetrics.productId,
                likeCount = productMetrics.likeCount,
                salesCount = productMetrics.salesCount,
                viewCount = productMetrics.viewCount,
            )
        }
        val savedEntity = productMetricsJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun findById(id: Long): ProductMetrics? {
        return productMetricsJpaRepository.findById(id)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun findByProductId(productId: Long): ProductMetrics? {
        return productMetricsJpaRepository.findByProductId(productId)?.toDomain()
    }

    @Transactional
    override fun findOrCreateByProductId(productId: Long): ProductMetrics {
        return findByProductId(productId) ?: run {
            val newMetrics = ProductMetrics.initialize(productId)
            save(newMetrics)
        }
    }

    override fun deleteById(id: Long) {
        productMetricsJpaRepository.deleteById(id)
    }

    override fun findTopByLikeCount(): List<ProductMetrics> {
        return productMetricsJpaRepository.findTop10ByOrderByLikeCountDesc()
            .map { it.toDomain() }
    }

    override fun findTopBySalesCount(): List<ProductMetrics> {
        return productMetricsJpaRepository.findTop10ByOrderBySalesCountDesc()
            .map { it.toDomain() }
    }

    private fun ProductMetricsEntity.toDomain(): ProductMetrics = ProductMetrics(
        id = this.id,
        productId = this.productId,
        likeCount = this.likeCount,
        salesCount = this.salesCount,
        viewCount = this.viewCount,
        createdAt = this.createdAt.toLocalDateTime(),
        updatedAt = this.updatedAt.toLocalDateTime(),
    )
}
