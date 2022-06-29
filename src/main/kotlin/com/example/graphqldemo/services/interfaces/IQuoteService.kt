package com.example.graphqldemo.services.interfaces

import com.example.graphqldemo.graphql.types.Quote
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

interface IQuoteService {
    @FlowPreview
    fun getQuotes(limit: UInt): Flow<Quote>
    suspend fun getRandomQuote(): Quote
    suspend fun getQuoteByType(type: Quote.QuoteType): Quote
    fun getQuotesByType(type: Quote.QuoteType, limit: UInt): Flow<Quote>
}
