package com.example.graphqldemo.graphql.types

import com.example.graphqldemo.graphql.types.cards.Card

data class Dashboard(
    val cards: List<Card>
)
