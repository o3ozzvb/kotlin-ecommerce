package com.loopers.interfaces.api.product

data class ProdustListRequest(
    val page: Int = 0,
    val size: Int = 20,
    val sort: String = "createdAt",
    val direction: String = "DESC",
)
