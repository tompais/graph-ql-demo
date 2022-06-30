package com.example.graphqldemo.config

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DispatcherConfig {
    @Bean(destroyMethod = "")
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
