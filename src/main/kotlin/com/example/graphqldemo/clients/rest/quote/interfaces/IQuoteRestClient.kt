package com.example.graphqldemo.clients.rest.quote.interfaces

import com.example.graphqldemo.graphql.types.quote.Quote
import kotlinx.coroutines.flow.Flow

interface IQuoteRestClient {
    fun getQuotes(limit: UInt): Flow<Quote>
    suspend fun getRandomQuote(): Quote
}
