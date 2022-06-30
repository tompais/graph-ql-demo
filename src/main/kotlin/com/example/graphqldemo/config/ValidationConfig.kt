package com.example.graphqldemo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.StandardReflectionParameterNameDiscoverer
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ValidationConfig {
    @Bean
    @Primary
    fun validator(): LocalValidatorFactoryBean = LocalValidatorFactoryBean().apply {
        setParameterNameDiscoverer(StandardReflectionParameterNameDiscoverer())
    }
}
