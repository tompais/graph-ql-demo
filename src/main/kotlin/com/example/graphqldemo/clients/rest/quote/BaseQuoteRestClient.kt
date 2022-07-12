package com.example.graphqldemo.clients.rest.quote

import com.example.graphqldemo.clients.rest.BaseRestClient
import com.example.graphqldemo.clients.rest.quote.config.properties.BaseQuoteProperties
import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteRestClient
import com.example.graphqldemo.graphql.types.quote.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import org.springframework.web.reactive.function.client.WebClient

abstract class BaseQuoteRestClient(
    webClient: WebClient,
    protected val quoteProperties: BaseQuoteProperties,
    val type: Quote.Type
) : IQuoteRestClient, BaseRestClient(webClient) {
    override fun getQuotes(limit: UInt): Flow<Quote> =
        getQuotes(quoteProperties.getQuotesPath).take(limit.toInt())

    private fun getQuotes(path: String): Flow<Quote> = getFlow(path)

    override suspend fun getRandomQuote(): Quote = get(quoteProperties.getRandomQuotePath)
}
