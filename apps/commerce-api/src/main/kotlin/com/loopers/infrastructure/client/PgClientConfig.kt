package com.loopers.infrastructure.client

import feign.Logger
import feign.Request
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class PgClientConfig {

    @Bean
    fun requestOptions(): Request.Options {
        return Request.Options(
            1000L, // connection timeout
            TimeUnit.MILLISECONDS,
            3000L, // read timeout
            TimeUnit.MILLISECONDS,
            true
        )
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }

    @Bean
    fun errorDecoder(): ErrorDecoder {
        return ErrorDecoder.Default()
    }
}
