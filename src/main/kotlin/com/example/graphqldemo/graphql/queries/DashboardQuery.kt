package com.example.graphqldemo.graphql.queries

import com.example.graphqldemo.graphql.types.Dashboard
import com.example.graphqldemo.services.interfaces.IDashboardService
import com.expediagroup.graphql.generator.annotations.GraphQLName
import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class DashboardQuery(private val dashboardService: IDashboardService) : Query {
    @GraphQLName("dashboard")
    fun getDashboard(): Dashboard = dashboardService.getDashboard()
}
