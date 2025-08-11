package com.loopers.interfaces.api.brand

import com.loopers.application.product.ProductFacade
import com.loopers.domain.brand.BrandService
import com.loopers.domain.product.Product
import com.loopers.interfaces.api.ApiResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/brands")
class BrandController(
    private val brandService: BrandService,
    private val productFacade: ProductFacade,
) {
    @GetMapping("/{brandId}")
    fun getBrandInfo(
        @PathVariable brandId: Long,
    ): ApiResponse<BrandDetailResponse> {
        val brand = brandService.findById(brandId)

        return ApiResponse.success(
            BrandDetailResponse(
                id = brand.id,
                name = brand.name,
                description = brand.description,
            ),
        )
    }

    @GetMapping("/{brandId}/products")
    fun getBrandProducts(
        @PathVariable brandId: Long,
        pageable: Pageable,
    ): ApiResponse<Page<Product>> {
        // 브랜드 존재 확인
        brandService.findById(brandId)

        val products = productFacade.findProducts(brandId, "name", pageable)
        return ApiResponse.success(products)
    }
}
