package com.example.graphqldemo.graphql.hooks

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLType
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Component
class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        LocalDate::class -> ExtendedScalars.Date
        BigDecimal::class -> ExtendedScalars.GraphQLBigDecimal
        else -> null
    }
}
