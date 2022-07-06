package com.example.graphqldemo.integration

import com.example.graphqldemo.utils.RestAssuredWebTestClientUtils.verifyOnlyDataExists
import org.junit.jupiter.api.Test

class QuoteIntegrationTest : BaseIntegrationTest() {
    @Test
    fun getQuotes() {
        repeat(3) {
            mockGetQuotesRequest()
        }

        testQuery("quotes(limit: 3) {quote, author}").verifyOnlyDataExists()
    }
}
