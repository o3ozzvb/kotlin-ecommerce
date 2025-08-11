package com.loopers.domain.product

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductMetricsService(
    private val productMetricsRepository: ProductMetricsRepository,
) {
    fun findByProductId(productId: Long): ProductMetrics? {
        return productMetricsRepository.findByProductId(productId)
    }

    fun getOrCreateMetrics(productId: Long): ProductMetrics {
        return findByProductId(productId) ?: run {
            val metrics = ProductMetrics.initialize(productId)
            productMetricsRepository.save(metrics)
        }
    }

    fun incrementLike(productId: Long): ProductMetrics {
        val metrics = getOrCreateMetrics(productId)
        val updatedMetrics = metrics.incrementLike()
        return productMetricsRepository.save(updatedMetrics)
    }

    fun decrementLike(productId: Long): ProductMetrics {
        val metrics = getOrCreateMetrics(productId)
        val updatedMetrics = metrics.decrementLike()
        return productMetricsRepository.save(updatedMetrics)
    }

    fun incrementView(productId: Long): ProductMetrics {
        val metrics = getOrCreateMetrics(productId)
        val updatedMetrics = metrics.incrementView()
        return productMetricsRepository.save(updatedMetrics)
    }
}
