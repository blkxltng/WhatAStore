package com.blkxltng.whatastore.cache

import com.blkxltng.whatastore.objects.StoresList
import com.epam.coroutinecache.annotations.*
import java.util.concurrent.TimeUnit

interface CacheProviders {
    @ProviderKey("StoresKey", EntryClass(StoresList::class))
    @LifeTime(value = 30, unit = TimeUnit.SECONDS) //Cache will persist for 30 seconds if a connection is available
    @Expirable
    @UseIfExpired
    suspend fun getStore(dataProvider: suspend () -> StoresList): StoresList
}

