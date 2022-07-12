package com.example.graphqldemo.clients.rest.quote.implementations

import com.example.graphqldemo.clients.rest.quote.BaseQuoteRestClient
import com.example.graphqldemo.clients.rest.quote.config.properties.AnimeQuoteProperties
import com.example.graphqldemo.graphql.types.quote.Quote.Type.ANIME
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class AnimeQuoteRestClient(
    animeQuoteWebClient: WebClient,
    animeQuoteProperties: AnimeQuoteProperties
) : BaseQuoteRestClient(animeQuoteWebClient, animeQuoteProperties, ANIME)
