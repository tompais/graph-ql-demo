package com.example.graphqldemo.graphql.instrumentations

import com.example.graphqldemo.utils.delegators.LoggerDelegate
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.ExecutionResult
import graphql.execution.instrumentation.InstrumentationContext
import graphql.execution.instrumentation.SimpleInstrumentation
import graphql.execution.instrumentation.SimpleInstrumentationContext
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

        return object : SimpleInstrumentationContext<ExecutionResult>() {
            override fun onCompleted(executionResult: ExecutionResult, t: Throwable?) {
                if (isInfoEnabled) {
                    val endMillis = System.currentTimeMillis()

                    if (t != null) {
                        logger.info("GraphQL execution {} failed: {}", executionId, t.message, t)
                    } else {
                        val resultMap = executionResult.toSpecification()
                        val resultJSON = mapper.writeValueAsString(resultMap).replace("\n", "\\n")

                        logger.info("[{}] completed in {}ms", executionId, endMillis - startMillis)
                        logger.info("[{}] result: {}", executionId, resultJSON)
                    }
                }
            }
        }
    }
}
