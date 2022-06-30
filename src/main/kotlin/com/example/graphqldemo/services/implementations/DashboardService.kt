package com.example.graphqldemo.services.implementations

import com.example.graphqldemo.graphql.types.dashboard.Dashboard
import com.example.graphqldemo.graphql.types.dashboard.cards.CreditCard
import com.example.graphqldemo.graphql.types.dashboard.cards.DebitCard
import com.example.graphqldemo.services.interfaces.IDashboardService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class DashboardService : IDashboardService {
    override fun getDashboard(): Dashboard {
        val dateFrom = LocalDate.now()
        val dateTo = dateFrom.plusYears(1)

        return Dashboard(
            listOf(
                DebitCard(1000f.toBigDecimal(), dateFrom, dateTo),
                CreditCard(CreditCard.Company.VISA, 1500f.toBigDecimal(), dateFrom, dateTo),
                CreditCard(CreditCard.Company.AMERICAN_EXPRESS, 1600f.toBigDecimal(), dateFrom, dateTo)
            )
        )
    }
}
