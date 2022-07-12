package com.example.graphqldemo.unit.services.implementations

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.graphqldemo.clients.cache.quote.interfaces.IQuoteCacheClient
import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteRestClient
import com.example.graphqldemo.graphql.types.quote.Quote.Type.PROGRAMMING
import com.example.graphqldemo.graphql.types.quote.Quote.Type.values
import com.example.graphqldemo.services.implementations.QuoteService
import com.example.graphqldemo.utils.MockUtils.mockQuote
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class QuoteServiceTest {
    private val quoteRestClientMock = mockk<IQuoteRestClient>()

    @SpyK
    private var quoteRestClients = listOf(quoteRestClientMock)

    @SpyK
    private var quoteRestClientMap = values().associateWith { quoteRestClientMock }

    @SpyK
    private var ioDispatcher = Dispatchers.IO

    @RelaxedMockK
    private lateinit var quoteCacheClient: IQuoteCacheClient

    @InjectMockKs
    private lateinit var quoteService: QuoteService

    @Test
    fun getQuoteByType() {
        // GIVEN
        val expectedQuote = mockQuote()

        coEvery { quoteCacheClient.getQuoteByTypeFromCacheAsync(any()) } returns mockk() {
            coEvery { await() } returns null
        }
        coEvery { quoteRestClientMock.getRandomQuote() } returns expectedQuote

        // WHEN

        val actualQuote = runBlocking {
            quoteService.getQuoteByType(PROGRAMMING)
        }

        // THEN
        assertThat(actualQuote).isEqualTo(expectedQuote)
    }
}
