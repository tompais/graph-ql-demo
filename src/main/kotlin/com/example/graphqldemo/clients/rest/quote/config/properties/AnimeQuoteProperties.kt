package com.example.graphqldemo.clients.rest.quote.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "clients.rest.quote.anime")
class AnimeQuoteProperties(getQuotesPath: String, getRandomQuotePath: String) : BaseQuoteProperties(
    getQuotesPath,
    getRandomQuotePath
)
