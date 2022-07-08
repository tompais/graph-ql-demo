package com.example.graphqldemo.graphql.error.handler

import com.example.graphqldemo.utils.delegators.LoggerDelegate
import graphql.ExceptionWhileDataFetching
import graphql.execution.SimpleDataFetcherExceptionHandler
import org.springframework.stereotype.Component

@Component
class CustomDataFetcherExceptionHandler : SimpleDataFetcherExceptionHandler() {
    private companion object {
        @JvmStatic
        private val logger by LoggerDelegate()
    }

    override fun logException(error: ExceptionWhileDataFetching?, exception: Throwable?) =
        logger.error(error?.message, exception)
}
