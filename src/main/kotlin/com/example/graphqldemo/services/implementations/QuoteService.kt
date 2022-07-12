package com.example.graphqldemo.services.implementations

import com.example.graphqldemo.clients.cache.quote.interfaces.IQuoteCacheClient
import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteRestClient
import com.example.graphqldemo.graphql.types.quote.Quote
import com.example.graphqldemo.services.interfaces.IQuoteService
import com.example.graphqldemo.utils.flow.FlowUtils.toShuffledList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class QuoteService(
    private val quoteRestClients: List<IQuoteRestClient>,
    private val quoteRestClientMap: Map<Quote.Type, IQuoteRestClient>,
    private val ioDispatcher: CoroutineDispatcher,
    private val quoteCacheClient: IQuoteCacheClient
) : IQuoteService {

    @FlowPreview
    override fun getQuotes(limit: UInt): Flow<Quote> =
        (getQuotesFromCache() ?: getQuotesAndPutIntoCache(limit)).take(limit.toInt())

    @FlowPreview
    private fun getQuotesAndPutIntoCache(limit: UInt): Flow<Quote> =
        flow {
            getQuotesOfAllTypes(limit).toShuffledList()
                .also { quotes -> cacheQuotes(quotes).also { emitAll(quotes.asFlow()) } }
        }

    private suspend fun cacheQuotes(quotes: List<Quote>) {
        quoteCacheClient.cacheQuotes(quotes)
    }

    private fun getQuotesFromCache(): Flow<Quote>? = quoteCacheClient.getQuotesFromCache()

    override suspend fun getRandomQuote(): Quote = getRandomQuoteFromCache() ?: getRandomQuoteAndPutIntoCache()

    private suspend fun getRandomQuoteAndPutIntoCache(): Quote =
        withContext(ioDispatcher) {
            getRandomQuoteClient().getRandomQuote().also { quote ->
                cacheQuote(quote)
            }
        }

    private suspend fun getRandomQuoteFromCache(): Quote? = quoteCacheClient.getQuoteFromCacheAsync().await()

    private fun getRandomQuoteClient(): IQuoteRestClient = getQuoteClientByType(Quote.Type.getRandomType())

    private fun getQuoteClientByType(type: Quote.Type): IQuoteRestClient = quoteRestClientMap.getValue(type)

    override suspend fun getQuoteByType(type: Quote.Type): Quote =
        getQuoteByTypeFromCache(type) ?: getQuoteByTypeAndPutIntoCache(type)

    private suspend fun getQuoteByTypeAndPutIntoCache(type: Quote.Type): Quote =
        withContext(ioDispatcher) {
            getQuoteClientByType(type).getRandomQuote()
                .also { quote -> cacheQuoteByType(type, quote) }
        }

    private suspend fun cacheQuoteByType(type: Quote.Type, quote: Quote) =
        quoteCacheClient.cacheQuoteByType(type, quote)

    private suspend fun cacheQuote(quote: Quote) = quoteCacheClient.cacheQuote(quote)

    private suspend fun getQuoteByTypeFromCache(type: Quote.Type): Quote? =
        quoteCacheClient.getQuoteByTypeFromCacheAsync(type).await()

    override fun getQuotesByType(type: Quote.Type, limit: UInt): Flow<Quote> =
        getQuoteClientByType(type).getQuotes(limit).flowOn(ioDispatcher)

    @FlowPreview
    private fun getQuotesOfAllTypes(limit: UInt): Flow<Quote> =
        quoteRestClients.asFlow().flatMapMerge(quoteRestClients.size) { it.getQuotes(limit) }.flowOn(ioDispatcher)
}
