package com.example.graphqldemo.graphql.instrumentations.context

import com.example.graphqldemo.utils.logger.delegators.LoggerDelegator
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.ExecutionId
import graphql.execution.instrumentation.SimpleInstrumentationContext

class RequestLoggingInstrumentationContext(
    private val executionId: ExecutionId,
    private val mapper: ObjectMapper
) : SimpleInstrumentationContext<ExecutionResult>() {
    private companion object {
        @JvmStatic
        private val logger by LoggerDelegator()
    }

    override fun onCompleted(executionResult: ExecutionResult, t: Throwable?) {
        if (logger.isInfoEnabled) {
            if (t != null) {
                logException(t)
            } else {
                logEndExecution()

                mapper.writeValueAsString(executionResult.toSpecification()).also(::logResult)
            }
        }
    }

    private fun logException(t: Throwable) = logger.info("GraphQL execution {} failed: {}", executionId, t.message, t)

    private fun logResult(resultJSON: String?) = logger.info("[{}] result: {}", executionId, resultJSON)

    private fun logEndExecution() = logger.info("[{}] completed", executionId)
}
