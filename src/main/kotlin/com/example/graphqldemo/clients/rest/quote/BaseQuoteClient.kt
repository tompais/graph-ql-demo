package com.example.graphqldemo.clients.rest.quote

import com.example.graphqldemo.clients.rest.BaseRestClient
import com.example.graphqldemo.clients.rest.quote.config.properties.BaseQuoteProperties
import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteClient
import com.example.graphqldemo.graphql.types.quote.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import org.springframework.web.reactive.function.client.WebClient

abstract class BaseQuoteClient(
    webClient: WebClient,
    protected val quoteProperties: BaseQuoteProperties,
    val type: Quote.Type
) : IQuoteClient, BaseRestClient(webClient) {
    override fun getQuotes(limit: UInt): Flow<Quote> =
        getQuotes(quoteProperties.getQuotesPath).take(limit.toInt())

    private fun getQuotes(path: String): Flow<Quote> = getFlow(path)

    override suspend fun getRandomQuote(): Quote = get(quoteProperties.getRandomQuotePath)
}
