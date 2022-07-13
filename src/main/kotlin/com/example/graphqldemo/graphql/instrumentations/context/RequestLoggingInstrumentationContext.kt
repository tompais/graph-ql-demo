package com.example.graphqldemo.graphql.instrumentations.context

import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.ExecutionId
import graphql.execution.instrumentation.SimpleInstrumentationContext
import org.apache.logging.log4j.kotlin.logger

class RequestLoggingInstrumentationContext(
    private val executionId: ExecutionId,
    private val mapper: ObjectMapper
) : SimpleInstrumentationContext<ExecutionResult>() {
    private val logger = logger()

    override fun onCompleted(executionResult: ExecutionResult, t: Throwable?) {
        if (logger.delegate.isInfoEnabled) {
            if (t != null) {
                logException(t)
            } else {
                logEndExecution()

                mapper.writeValueAsString(executionResult.toSpecification()).also(::logResult)
            }
        }
    }

    private fun logException(t: Throwable) = logger.info("GraphQL execution $executionId failed: $t.message", t)

    private fun logResult(resultJSON: String?) = logger.info("[$executionId] result: $resultJSON")

    private fun logEndExecution() = logger.info("[$executionId] completed")
}
