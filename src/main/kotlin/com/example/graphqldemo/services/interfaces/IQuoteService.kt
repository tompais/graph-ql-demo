package com.example.graphqldemo.services.interfaces

import com.example.graphqldemo.graphql.types.quote.Quote
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

interface IQuoteService {
    @FlowPreview
    fun getQuotes(limit: UInt): Flow<Quote>
    suspend fun getRandomQuote(): Quote
    suspend fun getQuoteByType(type: Quote.Type): Quote
    fun getQuotesByType(type: Quote.Type, limit: UInt): Flow<Quote>
}
