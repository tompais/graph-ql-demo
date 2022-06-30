package com.example.graphqldemo.clients.rest.quote.implementations

import com.example.graphqldemo.clients.rest.quote.BaseQuoteClient
import com.example.graphqldemo.clients.rest.quote.config.properties.ZenQuoteProperties
import com.example.graphqldemo.graphql.types.quote.Quote
import com.example.graphqldemo.graphql.types.quote.Quote.Type.ZEN
import kotlinx.coroutines.flow.first
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class ZenQuoteClient(zenQuoteWebClient: WebClient, zenQuoteProperties: ZenQuoteProperties) :
    BaseQuoteClient(zenQuoteWebClient, zenQuoteProperties, ZEN) {
    override suspend fun getRandomQuote(): Quote = getFlow<Quote>(quoteProperties.getRandomQuotePath).first()
}
