package com.blkxltng.whatastore.cache

import com.blkxltng.whatastore.objects.StoresList
import com.epam.coroutinecache.annotations.*
import java.util.concurrent.TimeUnit

interface CacheProviders {
    @ProviderKey("StoresKey", EntryClass(StoresList::class))
    @LifeTime(value = 1L, unit = TimeUnit.MINUTES)
    @Expirable
    @UseIfExpired
    suspend fun getStore(dataProvider: suspend () -> StoresList): StoresList
}

