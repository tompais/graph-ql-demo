package com.example.graphqldemo.clients.cache.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun caffeineConfig(): Caffeine<Any, Any> = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES)

    @Bean
    fun cacheManager(
        caffeine: Caffeine<Any, Any>,
        @Value("\${spring.cache.cache-names}") names: Array<String>
    ): CacheManager = CaffeineCacheManager(*names).apply {
        setCaffeine(caffeine)
        isAllowNullValues = false
    }
}
