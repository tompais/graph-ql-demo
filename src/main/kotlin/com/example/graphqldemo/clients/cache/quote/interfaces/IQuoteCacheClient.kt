package com.example.graphqldemo.clients.cache.quote.interfaces

import com.example.graphqldemo.graphql.types.quote.Quote
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow

interface IQuoteCacheClient {
    suspend fun cacheQuotes(quotes: List<Quote>)
    fun getQuotesFromCache(): Flow<Quote>?
    suspend fun cacheQuote(quote: Quote)
    suspend fun getQuoteFromCacheAsync(): Deferred<Quote?>
    suspend fun getQuoteByTypeFromCacheAsync(type: Quote.Type): Deferred<Quote?>
}
