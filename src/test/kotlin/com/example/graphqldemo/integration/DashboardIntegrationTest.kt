package com.example.graphqldemo.integration

import org.junit.jupiter.api.Test

class DashboardIntegrationTest : BaseIntegrationTest() {
    @Test
    fun getDashboard() {
        testQuery("dashboard {cards {type, ... on DebitCard {balance}, ... on CreditCard {availableForPurchases, company}}}")
    }
}
