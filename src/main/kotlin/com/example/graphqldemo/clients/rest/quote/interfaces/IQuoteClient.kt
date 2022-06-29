package com.example.graphqldemo.clients.rest.quote.interfaces

import com.example.graphqldemo.graphql.types.Quote
import kotlinx.coroutines.flow.Flow

interface IQuoteClient {
    fun getQuotes(limit: UInt): Flow<Quote>
    suspend fun getRandomQuote(): Quote
}
