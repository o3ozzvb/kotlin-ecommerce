package com.loopers.infrastructure.repository

import com.loopers.infrastructure.cache.CacheRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisCacheRepository(
    private val redisTemplate: RedisTemplate<String, String>
) : CacheRepository {
    override fun <T> get(key: String, clazz: Class<T>): T? {
        TODO("Not yet implemented")
    }

    override fun set(key: String, value: Any, ttlSeconds: Long?) {
        TODO("Not yet implemented")
    }

    override fun delete(key: String) {
        TODO("Not yet implemented")
    }

    override fun deleteByPattern(pattern: String): Long {
        TODO("Not yet implemented")
    }

    override fun exists(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getKeys(pattern: String): Set<String> {
        TODO("Not yet implemented")
    }

    override fun increment(key: String, delta: Long): Long {
        TODO("Not yet implemented")
    }

    override fun expire(key: String, ttlSeconds: Long): Boolean {
        TODO("Not yet implemented")
    }
}
