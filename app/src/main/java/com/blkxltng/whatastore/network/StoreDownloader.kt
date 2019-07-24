package com.blkxltng.whatastore.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity
import com.blkxltng.whatastore.objects.StoresList
import retrofit2.http.GET
import java.io.File

object StoreDownloader {

    //A retrofit Network Interface for the Api
    interface StoreApi{
        @GET("stores.json")
        suspend fun getStores(): StoresList
    }

    fun verifyAvailableNetwork(activity: FragmentActivity?): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo != null && networkInfo.isConnected
    }

    fun checkExists(path: String): Boolean {
        val file = File(path)
        return file.exists()
    }
}