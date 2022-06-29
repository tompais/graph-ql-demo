package com.example.graphqldemo.clients.rest

import kotlinx.coroutines.flow.Flow
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToFlow

abstract class BaseRestClient(
    protected val webClient: WebClient
) {
    protected inline fun <reified T> getFlow(path: String): Flow<T> = webClient.get()
        .uri(path)
        .retrieve()
        .bodyToFlow()

    protected suspend inline fun <reified T> get(path: String): T = webClient.get()
        .uri(path)
        .retrieve()
        .awaitBody()
}
