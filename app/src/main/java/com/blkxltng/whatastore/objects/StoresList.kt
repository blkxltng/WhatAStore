package com.blkxltng.whatastore.objects

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoresList(

    @SerializedName("stores")
    val stores: List<Store>

) : Parcelable