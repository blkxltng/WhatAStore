package com.blkxltng.whatastore.cache

import com.blkxltng.whatastore.Constants
import com.blkxltng.whatastore.network.StoreDownloader
import com.blkxltng.whatastore.objects.StoresList
import com.epam.coroutinecache.api.CacheParams
import com.epam.coroutinecache.api.CoroutinesCache
import com.epam.coroutinecache.mappers.GsonMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class Repository (private val cacheDirectory: File) {
    private val coroutinesCache = CoroutinesCache(CacheParams(10, GsonMapper(), cacheDirectory))
    private val restApi: StoreDownloader.StoreApi = Retrofit.Builder()
        .baseUrl(Constants.BOTTLEROCKET_STORE_API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(StoreDownloader.StoreApi::class.java)

    private val cacheProviders: CacheProviders = coroutinesCache.using(CacheProviders::class.java)

    suspend fun getStore(): StoresList = cacheProviders.getStore(restApi::getStores2)
}