package com.example.graphqldemo.unit.services.implementations

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.graphqldemo.clients.rest.quote.interfaces.IQuoteClient
import com.example.graphqldemo.graphql.types.quote.Quote.Type.PROGRAMMING
import com.example.graphqldemo.graphql.types.quote.Quote.Type.values
import com.example.graphqldemo.services.implementations.QuoteService
import com.example.graphqldemo.utils.MockUtils.mockQuote
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class QuoteServiceTest {
    private val quoteClientMock = mockk<IQuoteClient>()

    @SpyK
    private var quoteClients = listOf(quoteClientMock)

    @SpyK
    private var quoteClientMap = values().associateWith { quoteClientMock }

    @SpyK
    private var ioDispatcher = Dispatchers.IO

    @InjectMockKs
    private lateinit var quoteService: QuoteService

    @Test
    fun getQuoteByType() {
        // GIVEN
        val expectedQuote = mockQuote()

        coEvery { quoteClientMock.getRandomQuote() } returns expectedQuote

        // WHEN

        val actualQuote = runBlocking {
            quoteService.getQuoteByType(PROGRAMMING)
        }

        // THEN
        assertThat(actualQuote).isEqualTo(expectedQuote)
    }
}
