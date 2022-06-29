package com.example.graphqldemo.services.implementations

import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteClient
import com.example.graphqldemo.graphql.types.Quote
import com.example.graphqldemo.services.interfaces.IQuoteService
import com.example.graphqldemo.utils.FlowUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class QuoteService(
    private val quoteClients: List<IQuoteClient>,
    private val quoteClientMap: Map<Quote.QuoteType, IQuoteClient>,
    private val ioDispatcher: CoroutineDispatcher
) : IQuoteService {

    @FlowPreview
    override fun getQuotes(limit: UInt): Flow<Quote> =
        getQuotesOfAllTypes(limit).let(FlowUtils::shuffleFlow).take(limit.toInt())

    override suspend fun getRandomQuote(): Quote = withContext(ioDispatcher) {
        getRandomQuoteClient().getRandomQuote()
    }

    private fun getRandomQuoteClient(): IQuoteClient = getQuoteClientByType(Quote.QuoteType.getRandomType())

    private fun getQuoteClientByType(type: Quote.QuoteType): IQuoteClient = quoteClientMap[type]!!

    override suspend fun getQuoteByType(type: Quote.QuoteType): Quote = getQuoteClientByType(type).getRandomQuote()

    override fun getQuotesByType(type: Quote.QuoteType, limit: UInt): Flow<Quote> =
        getQuoteClientByType(type).getQuotes(limit).flowOn(ioDispatcher)

    @FlowPreview
    private fun getQuotesOfAllTypes(limit: UInt) =
        quoteClients.asFlow().flatMapMerge(quoteClients.size) { it.getQuotes(limit) }.flowOn(ioDispatcher)
}
