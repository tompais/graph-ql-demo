package com.example.graphqldemo.clients.rest.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS
import io.netty.handler.logging.LogLevel
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat
import java.time.Duration
import java.util.concurrent.TimeUnit.SECONDS

abstract class BaseWebClientConfig {
    private companion object {
        private const val TIMEOUT = 5L
    }

    protected fun buildWebClient(baseUrl: String, mapper: ObjectMapper, timeout: Long = TIMEOUT): WebClient =
        WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeaders { httpHeaders ->
                httpHeaders.apply {
                    accept = listOf(APPLICATION_JSON)
                    contentType = APPLICATION_JSON
                }
            }
            .clientConnector(ReactorClientHttpConnector(buildHttpClient(timeout)))
            .exchangeStrategies(buildExchangeStrategies(mapper))
            .build()

    private fun buildExchangeStrategies(mapper: ObjectMapper): ExchangeStrategies = ExchangeStrategies
        .builder()
        .codecs { clientDefaultCodecsConfigurer ->
            clientDefaultCodecsConfigurer.defaultCodecs().apply {
                jackson2JsonEncoder(Jackson2JsonEncoder(mapper, APPLICATION_JSON))
                jackson2JsonDecoder(Jackson2JsonDecoder(mapper, APPLICATION_JSON))
            }
        }.build()

    private fun buildHttpClient(timeout: Long): HttpClient = HttpClient.create()
        .option(CONNECT_TIMEOUT_MILLIS, timeout.times(1000).toInt())
        .responseTimeout(Duration.ofSeconds(timeout))
        .doOnConnected { conn ->
            conn.addHandlerLast(ReadTimeoutHandler(timeout, SECONDS))
                .addHandlerLast(WriteTimeoutHandler(timeout, SECONDS))
        }
        .wiretap(
            "reactor.netty.http.client.HttpClient",
            LogLevel.DEBUG,
            AdvancedByteBufFormat.TEXTUAL
        )
}
