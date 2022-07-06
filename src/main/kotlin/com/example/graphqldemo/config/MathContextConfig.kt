package com.example.graphqldemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.MathContext
import java.math.RoundingMode

@Configuration
class MathContextConfig {
    @Bean
    fun mathContext(): MathContext = MathContext(12, RoundingMode.DOWN)
}
