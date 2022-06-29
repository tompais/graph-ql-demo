package com.example.graphqldemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.example")
class GraphQlDemoApplication

fun main(args: Array<String>) {
    runApplication<GraphQlDemoApplication>(*args)
}
