package com.loopers.domain.brand

data class Brand(
    val id: Long,
    val name: String,
    val description: String,
) {
    init {
        require(name.isNotBlank()) { "Name cannot be blank" }
    }
}
