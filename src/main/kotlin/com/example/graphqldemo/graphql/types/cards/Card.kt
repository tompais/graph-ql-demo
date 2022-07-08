package com.example.graphqldemo.graphql.types.cards

import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id
import com.fasterxml.jackson.annotation.JsonValue
import java.time.LocalDate

@JsonTypeInfo(
    use = Id.NAME,
    include = As.EXISTING_PROPERTY,
    property = "type"
)
@JsonSubTypes(
    Type(value = DebitCard::class, name = "debitCard"),
    Type(value = CreditCard::class, name = "creditCard")
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
