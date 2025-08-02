package com.loopers.interfaces.api.product

import com.loopers.domain.product.Product
import com.loopers.domain.product.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService,
) {
    @GetMapping
    fun getProducts(
        @RequestParam(required = false) brandId: Long?,
        @RequestParam(defaultValue = "name") sortBy: String,
        pageable: Pageable,
    ): Page<Product> {
        return productService.findProducts(brandId, sortBy, pageable)
    }
}
