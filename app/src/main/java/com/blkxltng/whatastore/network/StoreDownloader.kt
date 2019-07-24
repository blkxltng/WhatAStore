package com.blkxltng.whatastore.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity
import com.blkxltng.whatastore.objects.StoresList
import retrofit2.http.GET

//fun getRetrofitInstance(): Retrofit? {
//    var retrofit: Retrofit? = null
//    if (retrofit == null) {
//        retrofit = Retrofit.Builder()
//            .baseUrl(Constants.BOTTLEROCKET_STORE_API_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    return retrofit
//}

object StoreDownloader {
//    fun retrofit() : Retrofit = Retrofit.Builder()
//        .baseUrl(Constants.BOTTLEROCKET_STORE_API_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

//    // Data Model for the Response returned from the Api
//    data class StoreResponse(
//        val results: List<Store>
//    )

//    data class StoreResponse(
//        val result: Call<StoresList?>?
//    )

    //A retrofit Network Interface for the Api
    interface StoreApi{
//        @GET("stores.json")
//        fun getStores(): Response<StoreResponse>
//
//        @GET("stores.json")
//        fun getAStore(): Call<StoresList>
//
        @GET("stores.json")
        suspend fun getStores2(): StoresList

//        @GET("stores.json")
//        fun getStores(): Call<StoresList>
    }

//    val storeApi : StoreApi = retrofit().create(StoreApi::class.java)



    fun verifyAvailableNetwork(activity: FragmentActivity?): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo != null && networkInfo.isConnected
    }

}