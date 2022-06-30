package com.example.graphqldemo.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList

object FlowUtils {
    fun <T> Flow<T>.shuffled(): Flow<T> =
        this.let { f -> flow { emitAll(f.toList().shuffled().asFlow()) }.flowOn(Dispatchers.Default) }
}
