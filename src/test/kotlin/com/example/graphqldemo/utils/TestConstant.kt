package com.example.graphqldemo.utils

import org.springframework.http.MediaType

object TestConstant {
    object GraphQL {
        const val BASE_URL = "/graphql"
        val CONTENT_TYPE = MediaType("application", "graphql").toString()
    }

    const val QUOTE = "quote"
    const val AUTHOR = "author"
}
