package com.example.graphqldemo.graphql.types.dashboard

import com.example.graphqldemo.graphql.types.dashboard.cards.Card

data class Dashboard(
    val cards: List<Card>
)
