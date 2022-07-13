package com.example.graphqldemo.clients.cache

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart.UNDISPATCHED
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.Cache

abstract class BaseCacheClient<T : Any>(
    private val cache: Cache
) {
    @Autowired
    private lateinit var unconfinedDispatcher: CoroutineDispatcher

    protected suspend fun cachePut(key: String, any: Any) {
        supervisorScope {
            launch(unconfinedDispatcher, UNDISPATCHED) {
                cache.putIfAbsent(key, any)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getFlowFromCache(key: String): Flow<T> =
        getValues(key).asFlow() as Flow<T>

    private fun getValues(key: String): List<Any?> = cache.get(key, List::class.java) ?: emptyList<Any>()

    protected suspend fun getFromCacheAsync(key: String): Deferred<T?> = supervisorScope {
        async {
            getValue(key)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getValue(key: String) = cache.get(key)?.get() as? T
}
