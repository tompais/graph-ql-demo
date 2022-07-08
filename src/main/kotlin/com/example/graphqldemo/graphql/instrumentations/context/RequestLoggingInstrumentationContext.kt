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
        if (logger.isInfoEnabled) {
            val endMillis = System.currentTimeMillis()

            if (t != null) {
                logger.info("GraphQL execution {} failed: {}", executionId, t.message, t)
            } else {
                val resultMap = executionResult.toSpecification()
                val resultJSON = mapper.writeValueAsString(resultMap)

                logger.info(
                    "[{}] completed in {}ms",
                    executionId,
                    endMillis - startMillis
                )
                logger.info("[{}] result: {}", executionId, resultJSON)
            }
        }
    }
}
