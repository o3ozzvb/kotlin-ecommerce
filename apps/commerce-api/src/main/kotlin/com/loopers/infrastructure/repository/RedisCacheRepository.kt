package com.loopers.infrastructure.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.loopers.infrastructure.cache.CacheRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
class RedisCacheRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) : CacheRepository {
    override fun <T> get(key: String, clazz: Class<T>): T? {
        val json = redisTemplate.opsForValue().get(key) ?: return null
        return try {
            objectMapper.readValue(json, clazz)
        } catch (e: Exception) {
            null
        }
    }

    override fun set(key: String, value: Any, ttlSeconds: Long?) {
        val json = objectMapper.writeValueAsString(value)
        if (ttlSeconds != null) {
            redisTemplate.opsForValue().set(key, json, ttlSeconds, TimeUnit.SECONDS)
        } else {
            redisTemplate.opsForValue().set(key, json)
        }
    }
//    override fun delete(key: String) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteByPattern(pattern: String): Long {
//        TODO("Not yet implemented")
//    }
//
//    override fun exists(key: String): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun getKeys(pattern: String): Set<String> {
//        TODO("Not yet implemented")
//    }
//
//    override fun increment(key: String, delta: Long): Long {
//        TODO("Not yet implemented")
//    }
//
//    override fun expire(key: String, ttlSeconds: Long): Boolean {
//        TODO("Not yet implemented")
//    }
}
