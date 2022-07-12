package com.example.graphqldemo.clients.cache.quote.implementations

import com.example.graphqldemo.clients.cache.BaseCacheClient
import com.example.graphqldemo.clients.cache.quote.interfaces.IQuoteCacheClient
import com.example.graphqldemo.graphql.types.quote.Quote
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import org.springframework.cache.Cache
import org.springframework.stereotype.Component

@Component
class QuoteCacheClient(quoteCache: Cache) : BaseCacheClient<Quote>(quoteCache), IQuoteCacheClient {
    private companion object KeyPrefix {
        private const val QUOTES = "quotes"
        private const val QUOTE = "quote"
    }

    override suspend fun cacheQuotes(quotes: List<Quote>) = cachePut(QUOTES, quotes)
    override fun getQuotesFromCache(): Flow<Quote>? = getFlowFromCache(QUOTES)
    override suspend fun cacheQuote(quote: Quote) = cachePut(QUOTE, quote)
    override suspend fun getQuoteFromCacheAsync(): Deferred<Quote?> = getFromCacheAsync(QUOTE)
    override suspend fun getQuoteByTypeFromCacheAsync(type: Quote.Type): Deferred<Quote?> =
        getFromCacheAsync("$QUOTE-$type")

    override suspend fun cacheQuoteByType(type: Quote.Type, quote: Quote) = cachePut("$QUOTE-$type", quote)
}
