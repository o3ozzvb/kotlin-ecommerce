package com.loopers.infrastructure.cache

data class ProductListCacheKey(
    val brandId: Long? = null,
    val sortBy: String = "likes",
    val page: Int,
    val size: Int = 20,
) {
    fun toRedisKey(): String {
        val filters = mutableListOf<String>()
        brandId?.let { filters.add("brand_$it") }

        val filterStr = if (filters.isEmpty()) "all" else filters.joinToString("_")
        return "products:list:$sortBy:$filterStr:p${page}_s$size"
    }
}
