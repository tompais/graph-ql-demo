package com.example.graphqldemo.services.interfaces

import com.example.graphqldemo.graphql.types.Dashboard

interface IDashboardService {
    fun getDashboard(): Dashboard
}
