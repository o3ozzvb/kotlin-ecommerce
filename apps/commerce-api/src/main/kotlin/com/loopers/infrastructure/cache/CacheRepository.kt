package com.loopers.infrastructure.cache

interface CacheRepository {
    fun <T> get(key: String, clazz: Class<T>): T?
    fun set(key: String, value: Any, ttlSeconds: Long? = null)
    fun delete(key: String)
    fun deleteByPattern(pattern: String): Long
    fun exists(key: String): Boolean
    fun getKeys(pattern: String): Set<String>
    fun increment(key: String, delta: Long = 1): Long
    fun expire(key: String, ttlSeconds: Long): Boolean
}
