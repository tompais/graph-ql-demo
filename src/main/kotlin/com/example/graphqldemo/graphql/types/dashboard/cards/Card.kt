package com.example.graphqldemo.graphql.types.dashboard.cards

import com.expediagroup.graphql.generator.annotations.GraphQLName
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
    val type: Type,
    val dateFrom: LocalDate,
    val dateTo: LocalDate
) {
    @GraphQLName("CardType")
    enum class Type {
        DEBIT,
        CREDIT;

        @JsonValue
        override fun toString(): String = name.lowercase()
    }
}
