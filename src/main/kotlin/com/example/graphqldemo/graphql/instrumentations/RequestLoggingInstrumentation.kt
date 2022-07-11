package com.example.graphqldemo.graphql.instrumentations

import com.example.graphqldemo.graphql.instrumentations.context.RequestLoggingInstrumentationContext
import com.example.graphqldemo.utils.delegators.LoggerDelegator
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.ExecutionId
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import org.springframework.stereotype.Component

@Component
class RequestLoggingInstrumentation(private val mapper: ObjectMapper) : SimpleInstrumentation() {
    private companion object {
        @JvmStatic
        private val logger by LoggerDelegator()
    }

    override fun beginExecution(parameters: InstrumentationExecutionParameters): InstrumentationContext<ExecutionResult> =
        System.currentTimeMillis().let { startMillis ->
            if (logger.isInfoEnabled) {
                parameters.executionInput.executionId.let { executionId ->
                    logStartExecution(executionId).also {
                        logQuery(executionId, parameters.query).also {
                            logVariables(parameters.variables, executionId)
                        }
                    }

                    RequestLoggingInstrumentationContext(startMillis, executionId, mapper)
                }
            } else {
                super.beginExecution(parameters)
            }
        }

    private fun logStartExecution(executionId: ExecutionId?) = logger.info("GraphQL execution {} started", executionId)

    private fun logVariables(
        variables: Map<String, Any>,
        executionId: ExecutionId?
    ) {
        if (variables.isNotEmpty()) {
            logger.info("[{}] variables: {}", executionId, variables)
        }
    }

    private fun logQuery(executionId: ExecutionId?, query: String?) = logger.info("[{}] query: {}", executionId, query)
}
