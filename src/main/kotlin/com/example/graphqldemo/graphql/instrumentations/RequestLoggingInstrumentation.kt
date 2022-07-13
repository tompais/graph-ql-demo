package com.example.graphqldemo.graphql.instrumentations

import com.example.graphqldemo.graphql.instrumentations.context.RequestLoggingInstrumentationContext
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.ExecutionId
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import org.apache.logging.log4j.kotlin.logger
import org.springframework.stereotype.Component

@Component
class RequestLoggingInstrumentation(private val mapper: ObjectMapper) : SimpleInstrumentation() {
    private val logger = logger()

    override fun beginExecution(parameters: InstrumentationExecutionParameters): InstrumentationContext<ExecutionResult> =
        if (logger.delegate.isInfoEnabled) {
            parameters.executionInput.executionId.let { executionId ->
                logStartExecution(executionId).also {
                    logQuery(executionId, parameters.query).also {
                        logVariables(parameters.variables, executionId)
                    }
                }

                RequestLoggingInstrumentationContext(executionId, mapper)
            }
        } else {
            super.beginExecution(parameters)
        }

    private fun logStartExecution(executionId: ExecutionId?) = logger.info("GraphQL execution $executionId started")

    private fun logVariables(
        variables: Map<String, Any>,
        executionId: ExecutionId?
    ) {
        if (variables.isNotEmpty()) {
            logger.info("[$executionId] variables: $variables")
        }
    }

    private fun logQuery(executionId: ExecutionId?, query: String?) = logger.info("[$executionId] query: $query")
}
