package com.blkxltng.whatastore.cache

import com.blkxltng.whatastore.objects.Store
import com.blkxltng.whatastore.objects.StoresList
import com.epam.coroutinecache.annotations.*
import com.epam.coroutinecache.api.CacheParams
import com.epam.coroutinecache.api.CoroutinesCache
import com.epam.coroutinecache.mappers.GsonMapper
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit

interface CacheProviders {
    @ProviderKey("TestKey", EntryClass(StoresList::class))
    @LifeTime(value = 1L, unit = TimeUnit.MINUTES)
    @Expirable
    @UseIfExpired
    suspend fun getStore(dataProvider: suspend () -> StoresList): StoresList
}

