package com.loopers

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.TimeZone

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.loopers.infrastructure.repository"])
@EnableFeignClients(basePackages = ["com.loopers.infrastructure.client"])
class CommerceApiApplication {

    @PostConstruct
    fun started() {
        // set timezone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}

fun main(args: Array<String>) {
    runApplication<CommerceApiApplication>(*args)
}
