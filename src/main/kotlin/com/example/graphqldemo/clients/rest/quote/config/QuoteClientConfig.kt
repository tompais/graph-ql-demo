package com.example.graphqldemo.clients.rest.quote.config

import com.example.graphqldemo.clients.rest.config.BaseWebClientConfig
import com.example.graphqldemo.clients.rest.quote.BaseQuoteClient
import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteClient
import com.example.graphqldemo.graphql.types.quote.Quote
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class QuoteClientConfig(
    private val mapper: ObjectMapper
) : BaseWebClientConfig() {
    @Bean
    fun animeQuoteWebClient(@Value("\${clients.rest.quote.anime.base-url}") baseUrl: String): WebClient =
        buildWebClient(baseUrl, mapper)

    @Bean
    fun programmingQuoteWebClient(@Value("\${clients.rest.quote.programming.base-url}") baseUrl: String): WebClient =
        buildWebClient(baseUrl, mapper)

    @Bean
    fun zenQuoteWebClient(@Value("\${clients.rest.quote.zen.base-url}") baseUrl: String): WebClient =
        buildWebClient(baseUrl, mapper)

    @Bean
    fun quoteClientMap(
        quoteClients: List<BaseQuoteClient>
    ): Map<Quote.Type, IQuoteClient> = quoteClients.associateBy(BaseQuoteClient::type)
}
