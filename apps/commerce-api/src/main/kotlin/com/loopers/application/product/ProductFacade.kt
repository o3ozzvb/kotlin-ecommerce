package com.loopers.application.product

import com.loopers.domain.brand.BrandService
import com.loopers.domain.inventory.InventoryService
import com.loopers.domain.member.MemberService
import com.loopers.domain.product.LikeService
import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductMetricsService
import com.loopers.domain.product.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductFacade(
    private val productService: ProductService,
    private val brandService: BrandService,
    private val inventoryService: InventoryService,
    private val likeService: LikeService,
    private val productMetricsService: ProductMetricsService,
    private val memberService: MemberService,
) {
    fun findProducts(brandId: Long?, sortBy: String, pageable: Pageable): Page<Product> {
        val productDataPage = productService.findProductsData(brandId, sortBy, pageable)

        val products = productDataPage.content.map { productData ->
            val brand = brandService.findById(productData.brandId)
            val inventory = inventoryService.findById(productData.inventoryId)
            productData.toProduct(brand, inventory)
        }

        return PageImpl(products, pageable, productDataPage.totalElements)
    }

    fun findProductDetail(productId: Long, userId: Long?): ProductDetailInfo {
        val productData = productService.findById(productId)
            ?: throw IllegalArgumentException("Product not found: $productId")

        val metrics = productMetricsService.getOrCreateMetrics(productId)

        productMetricsService.incrementView(productId)

        val isLiked = userId?.let { likeService.isLikedByUser(it, productId) } ?: false

        val brand = brandService.findById(productData.brandId)
        val inventory = inventoryService.findById(productData.inventoryId)
        val product = productData.toProduct(brand, inventory)

        return ProductDetailInfo(
            product = product,
            likeCount = metrics.likeCount,
            isLiked = isLiked,
        )
    }

    fun likeProduct(productId: Long, userId: Long) {
        memberService.findById(userId)

        productService.findById(productId)
            ?: throw IllegalArgumentException("Product not found: $productId")

        val existingLike = likeService.isLikedByUser(userId, productId)
        if (!existingLike) {
            likeService.like(userId, productId)
            productMetricsService.incrementLike(productId)
        }
    }

    fun unlikeProduct(productId: Long, userId: Long) {
        memberService.findById(userId)

        productService.findById(productId)
            ?: throw IllegalArgumentException("Product not found: $productId")

        val existingLike = likeService.isLikedByUser(userId, productId)
        if (existingLike) {
            likeService.unlike(userId, productId)
            productMetricsService.decrementLike(productId)
        }
    }

    fun findLikedProducts(userId: Long, pageable: Pageable): Page<Product> {
        memberService.findById(userId)

        val likes = likeService.findLikesByMember(userId)
        val productIds = likes.map { it.productId }

        if (productIds.isEmpty()) {
            return PageImpl(emptyList(), pageable, 0)
        }

        val startIndex = pageable.offset.toInt()
        val endIndex = minOf(startIndex + pageable.pageSize, productIds.size)

        if (startIndex >= productIds.size) {
            return PageImpl(emptyList(), pageable, productIds.size.toLong())
        }

        val pageProductIds = productIds.subList(startIndex, endIndex)
        val products = pageProductIds.mapNotNull { productId ->
            productService.findById(productId)?.let { productData ->
                val brand = brandService.findById(productData.brandId)
                val inventory = inventoryService.findById(productData.inventoryId)
                productData.toProduct(brand, inventory)
            }
        }

        return PageImpl(products, pageable, productIds.size.toLong())
    }
}
