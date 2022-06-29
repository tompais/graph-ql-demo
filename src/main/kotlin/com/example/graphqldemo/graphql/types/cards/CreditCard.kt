package com.example.graphqldemo.graphql.types.cards

import com.example.graphqldemo.graphql.types.cards.Card.CardType.CREDIT
import com.fasterxml.jackson.annotation.JsonValue
import java.math.BigDecimal
import java.time.LocalDate

class CreditCard(
    val company: Company,
    val availableForPurchases: BigDecimal,
    dateFrom: LocalDate,
    dateTo: LocalDate
) : Card(
    CREDIT,
    dateFrom,
    dateTo
) {
    enum class Company {
        VISA,
        AMERICAN_EXPRESS;

        @JsonValue
        override fun toString(): String = name.lowercase()
    }
}
