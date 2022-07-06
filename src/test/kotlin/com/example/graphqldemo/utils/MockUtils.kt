package com.example.graphqldemo.utils

import com.example.graphqldemo.graphql.types.quote.Quote
import com.example.graphqldemo.utils.TestConstant.AUTHOR
import com.example.graphqldemo.utils.TestConstant.QUOTE

object MockUtils {
    fun mockQuote(quote: String = QUOTE, author: String = AUTHOR): Quote = Quote(quote, author)
}
