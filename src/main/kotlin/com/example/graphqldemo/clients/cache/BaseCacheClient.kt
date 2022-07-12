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

    protected suspend fun cachePut(key: String, values: List<T>) {
        supervisorScope {
            launch(unconfinedDispatcher, UNDISPATCHED) {
                cache.putIfAbsent(key, values)
            }
        }
    }

    protected suspend fun cachePut(key: String, value: T) {
        supervisorScope {
            launch(unconfinedDispatcher, UNDISPATCHED) {
                cache.putIfAbsent(key, value)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getFlowFromCache(key: String): Flow<T>? =
        (cache.get(key, List::class.java)?.asFlow()) as? Flow<T>

    @Suppress("UNCHECKED_CAST")
    protected suspend fun getFromCacheAsync(key: String): Deferred<T?> = supervisorScope {
        async {
            cache.get(key)?.get() as? T
        }
    }
}
