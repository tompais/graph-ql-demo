package com.example.graphqldemo.clients.rest.quote.implementations

import com.example.graphqldemo.clients.rest.quote.BaseQuoteClient
import com.example.graphqldemo.clients.rest.quote.config.properties.ProgrammingQuoteProperties
import com.example.graphqldemo.graphql.types.Quote.QuoteType.PROGRAMMING
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ProgrammingQuoteClient(
    @Qualifier("programmingQuoteWebClient") webClient: WebClient,
    programmingQuoteProperties: ProgrammingQuoteProperties
) : BaseQuoteClient(webClient, programmingQuoteProperties, PROGRAMMING)
