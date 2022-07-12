package com.example.graphqldemo.clients.rest.quote.implementations

import com.example.graphqldemo.clients.rest.quote.BaseQuoteRestClient
import com.example.graphqldemo.clients.rest.quote.config.properties.ProgrammingQuoteProperties
import com.example.graphqldemo.graphql.types.quote.Quote.Type.PROGRAMMING
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ProgrammingQuoteRestClient(
    programmingQuoteWebClient: WebClient,
    programmingQuoteProperties: ProgrammingQuoteProperties
) : BaseQuoteRestClient(programmingQuoteWebClient, programmingQuoteProperties, PROGRAMMING)
