package com.example.graphqldemo.graphql.types

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
    enum class QuoteType {
        ANIME,
        PROGRAMMING,
        ZEN;

        @JsonValue
        override fun toString(): String = name.lowercase()

        companion object {
            private val VALUES = values()

            fun getRandomType(): QuoteType = VALUES.random()
        }
    }
}
