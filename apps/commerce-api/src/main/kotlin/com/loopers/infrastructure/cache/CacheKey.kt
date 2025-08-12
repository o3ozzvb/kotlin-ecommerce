package com.loopers.infrastructure.cache

object CacheKey {
    private const val PREFIX = "products"

    fun productListKey(
        sortBy: String = "likes",
        page: Int,
        size: Int = 20,
        categoryId: Long? = null,
        brandId: Long? = null
    ): String {
        val filters = mutableListOf<String>()
        categoryId?.let { filters.add("cat_$it") }
        brandId?.let { filters.add("brand_$it") }

        val filterStr = if (filters.isEmpty()) "all" else filters.joinToString("_")
        return "$PREFIX:list:$sortBy:$filterStr:p${page}_s$size"
    }

    fun productDetailKey(productId: Long): String {
        return "$PREFIX:detail:$productId"
    }
}
