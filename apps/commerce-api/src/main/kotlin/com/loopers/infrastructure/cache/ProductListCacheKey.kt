package com.loopers.infrastructure.cache

data class ProductListCacheKey(
    val sortBy: String = "likes",
    val page: Int,
    val size: Int = 20,
    val categoryId: Long? = null,
    val brandId: Long? = null,
) {
    fun toRedisKey(): String {
        val filters = mutableListOf<String>()
        categoryId?.let { filters.add("cat_$it") }
        brandId?.let { filters.add("brand_$it") }

        val filterStr = if (filters.isEmpty()) "all" else filters.joinToString("_")
        return "products:list:$sortBy:$filterStr:p${page}_s$size"
    }
}
