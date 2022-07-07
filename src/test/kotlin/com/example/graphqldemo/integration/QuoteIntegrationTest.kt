package com.example.graphqldemo.integration

import com.example.graphqldemo.graphql.types.quote.Quote
import com.example.graphqldemo.utils.MockUtils.mockQuote
import com.example.graphqldemo.utils.RestAssuredWebTestClientUtils.verifyOnlyDataExists
import org.junit.jupiter.api.Test

class QuoteIntegrationTest : BaseIntegrationTest() {
    @Test
    fun getQuotes() {
        mockGetQuotesClientRequest()

        testQuery("quotes(limit: 3) {quote, author}").verifyOnlyDataExists()
    }

    private fun mockGetQuotesClientRequest(times: Int = 3) {
        repeat(times) {
            mockRestClientRequest(mockQuote())
        }
    }

    @Test
    fun getRandomQuote() {
        mockRestClientRequest(mockQuote())

        testQuery("randomQuote {quote, author}").verifyOnlyDataExists()
    }

    @Test
    fun getQuoteByType() {
        mockRestClientRequest(mockQuote())

        testQuery("quoteByType(type: ${Quote.Type.getRandomType().name}) {quote, author}").verifyOnlyDataExists()
    }

    @Test
    fun getQuotesByType() {
        mockGetQuotesClientRequest()

        testQuery("quotesByType(type: ${Quote.Type.getRandomType().name}, limit: 3) {quote, author}").verifyOnlyDataExists()
    }
}
