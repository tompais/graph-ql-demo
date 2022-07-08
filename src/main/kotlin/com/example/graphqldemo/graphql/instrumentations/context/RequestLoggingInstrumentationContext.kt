package com.example.graphqldemo.graphql.instrumentations.context

import com.example.graphqldemo.utils.delegators.LoggerDelegate
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.ExecutionId
import graphql.execution.instrumentation.SimpleInstrumentationContext

class RequestLoggingInstrumentationContext(
    private val startMillis: Long,
    private val executionId: ExecutionId,
    private val mapper: ObjectMapper
) : SimpleInstrumentationContext<ExecutionResult>() {
    private companion object {
        @JvmStatic
        private val logger by LoggerDelegate()
    }

    override fun onCompleted(executionResult: ExecutionResult, t: Throwable?) {
        System.currentTimeMillis().also { endMillis ->
            if (logger.isInfoEnabled) {
                if (t != null) {
                    logException(t)
                } else {
                    logEndExecution(endMillis)

                    mapper.writeValueAsString(executionResult.toSpecification()).also(::logResult)
                }
            }
        }
    }

    private fun logException(t: Throwable) = logger.info("GraphQL execution {} failed: {}", executionId, t.message, t)

    private fun logResult(resultJSON: String?) = logger.info("[{}] result: {}", executionId, resultJSON)

    private fun logEndExecution(endMillis: Long) = logger.info(
        "[{}] completed in {}ms",
        executionId,
        endMillis - startMillis
    )
}
