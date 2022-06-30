package com.example.graphqldemo.services.interfaces

import com.example.graphqldemo.graphql.types.dashboard.Dashboard

interface IDashboardService {
    fun getDashboard(): Dashboard
}
