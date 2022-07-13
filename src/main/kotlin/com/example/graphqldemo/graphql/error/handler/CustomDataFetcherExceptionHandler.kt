package com.example.graphqldemo.graphql.error.handler

import graphql.ExceptionWhileDataFetching
import graphql.execution.SimpleDataFetcherExceptionHandler
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Component

@Component
class CustomDataFetcherExceptionHandler : SimpleDataFetcherExceptionHandler() {
    private val logger = logger()

    override fun logException(error: ExceptionWhileDataFetching, exception: Throwable) =
        logger.error(error.message, exception)
}
