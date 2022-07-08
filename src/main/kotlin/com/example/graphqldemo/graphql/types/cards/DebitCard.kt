package com.example.graphqldemo.graphql.types.cards

import com.example.graphqldemo.graphql.types.cards.Card.Type.DEBIT
import java.math.BigDecimal
import java.time.LocalDate

class DebitCard(
    val balance: BigDecimal,
    dateFrom: LocalDate,
    dateTo: LocalDate
) : Card(
    DEBIT,
    dateFrom,
    dateTo
)
