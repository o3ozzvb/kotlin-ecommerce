package com.loopers.interfaces.api.product

import com.loopers.application.product.ProductDetailInfo
import com.loopers.application.product.ProductFacade
import com.loopers.domain.product.Product
import com.loopers.interfaces.api.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productFacade: ProductFacade,
) {
    @GetMapping
    fun getProducts(
        @RequestParam(required = false) brandId: Long?,
        @RequestParam(defaultValue = "name") sortBy: String,
        pageable: Pageable,
    ): Page<Product> {
        return productFacade.findProducts(brandId, sortBy, pageable)
    }

    @GetMapping("/{productId}")
    fun getProductDetail(
        @PathVariable productId: Long,
        @RequestHeader(value = "X-USER-ID", required = false) userId: Long?,
    ): ApiResponse<ProductDetailInfo> {
        val productDetail = productFacade.findProductDetail(productId, userId)
        return ApiResponse.success(productDetail)
    }

    @PostMapping("/{productId}/like")
    @ResponseStatus(HttpStatus.OK)
    fun likeProduct(
        @PathVariable productId: Long,
        @RequestHeader("X-USER-ID") userId: Long,
    ): ApiResponse<Unit> {
        productFacade.likeProduct(productId, userId)
        return ApiResponse.success(Unit)
    }

    @PostMapping("/{productId}/unlike")
    @ResponseStatus(HttpStatus.OK)
    fun unlikeProduct(
        @PathVariable productId: Long,
        @RequestHeader("X-USER-ID") userId: Long,
    ): ApiResponse<Unit> {
        productFacade.unlikeProduct(productId, userId)
        return ApiResponse.success(Unit)
    }

    @GetMapping("/liked")
    fun getLikedProducts(
        @RequestHeader("X-USER-ID") userId: Long,
        pageable: Pageable,
    ): ApiResponse<Page<Product>> {
        val likedProducts = productFacade.findLikedProducts(userId, pageable)
        return ApiResponse.success(likedProducts)
    }
}
