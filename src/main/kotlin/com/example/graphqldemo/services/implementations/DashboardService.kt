package com.example.graphqldemo.services.implementations

import com.example.graphqldemo.graphql.types.dashboard.Dashboard
import com.example.graphqldemo.graphql.types.dashboard.cards.Card
import com.example.graphqldemo.graphql.types.dashboard.cards.CreditCard
import com.example.graphqldemo.graphql.types.dashboard.cards.DebitCard
import com.example.graphqldemo.services.interfaces.IDashboardService
import org.springframework.stereotype.Service
import java.math.MathContext
import java.time.LocalDate

@Service
class DashboardService(private val mathContext: MathContext) : IDashboardService {
    override fun getDashboard(): Dashboard = Dashboard(
        buildCards()
    )

    private fun buildCards(): List<Card> = LocalDate.now().let { dateFrom ->
        dateFrom.plusYears(1).let { dateTo ->
            listOf(
                DebitCard(1000f.toBigDecimal(mathContext), dateFrom, dateTo),
                CreditCard(CreditCard.Company.VISA, 1500f.toBigDecimal(mathContext), dateFrom, dateTo),
                CreditCard(
                    CreditCard.Company.AMERICAN_EXPRESS,
                    1600f.toBigDecimal(mathContext),
                    dateFrom,
                    dateTo
                )
            )
        }
    }
}
