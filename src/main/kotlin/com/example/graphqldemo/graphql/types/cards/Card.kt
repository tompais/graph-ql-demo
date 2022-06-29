package com.example.graphqldemo.graphql.types.cards

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDate

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = DebitCard::class, name = "debitCard"),
    JsonSubTypes.Type(value = CreditCard::class, name = "creditCard")
)
abstract class Card(
    val type: CardType,
    val dateFrom: LocalDate,
    val dateTo: LocalDate
) {
    enum class CardType {
        DEBIT,
        CREDIT;

        @JsonValue
        override fun toString(): String = name.lowercase()
    }
}
