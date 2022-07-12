package com.example.graphqldemo.utils.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

object FlowUtils {
    suspend fun <T> Flow<T>.toShuffledList(): List<T> =
        toList().shuffled()
}
