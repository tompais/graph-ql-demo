package com.example.graphqldemo.graphql.queries

import com.example.graphqldemo.graphql.types.Quote
import com.example.graphqldemo.services.interfaces.IQuoteService
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.server.operations.Query
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
import javax.validation.constraints.Positive

@Component
@Validated
class QuoteQuery(private val quoteService: IQuoteService) : Query {
    @FlowPreview
    @GraphQLName("quotes")
    suspend fun getQuotes(
        @Positive
        @Max(10)
        limit: Int? = 10
    ): List<Quote> = quoteService.getQuotes(limit!!.toUInt()).toList()

    @GraphQLName("randomQuote")
    suspend fun getRandomQuote(): Quote = quoteService.getRandomQuote()

    @GraphQLName("quoteByType")
    suspend fun getQuoteByType(type: Quote.QuoteType): Quote = quoteService.getQuoteByType(type)

    @GraphQLName("quotesByType")
    suspend fun getQuotesByType(
        type: Quote.QuoteType,
        @Positive
        @Max(10)
        limit: Int? = 10
    ): List<Quote> = quoteService.getQuotesByType(type, limit!!.toUInt()).toList()
}
