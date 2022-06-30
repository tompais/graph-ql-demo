package com.example.graphqldemo.graphql.types.quote

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonValue
import javax.validation.constraints.NotBlank

data class Quote(
    @field:NotBlank
    @JsonAlias("en", "q")
    val quote: String,

    @field:NotBlank
    @JsonAlias("character", "a")
    val author: String
) {
    @GraphQLName("QuoteType")
    enum class Type {
        ANIME,
        PROGRAMMING,
        ZEN;

        @JsonValue
        override fun toString(): String = name.lowercase()

        companion object {
            private val VALUES = values()

            fun getRandomType(): Type = VALUES.random()
        }
    }
}
