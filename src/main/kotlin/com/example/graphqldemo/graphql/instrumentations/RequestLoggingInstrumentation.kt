package com.example.graphqldemo.graphql.instrumentations

import com.example.graphqldemo.graphql.instrumentations.context.RequestLoggingInstrumentationContext
import com.example.graphqldemo.utils.delegators.LoggerDelegate
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters
import org.springframework.stereotype.Component

@Component
class RequestLoggingInstrumentation(private val mapper: ObjectMapper) : SimpleInstrumentation() {
    private companion object {
        private val logger by LoggerDelegate()
    }

    override fun beginExecution(parameters: InstrumentationExecutionParameters): InstrumentationContext<ExecutionResult> {
        val startMillis = System.currentTimeMillis()
        val executionId = parameters.executionInput.executionId

        val isInfoEnabled = logger.isInfoEnabled
        if (isInfoEnabled) {
            logger.info("GraphQL execution {} started", executionId)

            val query = parameters.query
            logger.info("[{}] query: {}", executionId, query)

            val variables = parameters.variables
            if (variables != null && variables.isNotEmpty()) {
                logger.info("[{}] variables: {}", executionId, variables)
            }
        }

        return RequestLoggingInstrumentationContext(startMillis, executionId, mapper)
    }
}
