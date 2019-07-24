package com.blkxltng.whatastore.objects

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Store(

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("zipcode")
    val zipCode: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("storeID")
    val storeId: String,

    @SerializedName("storeLogoURL")
    val logoURL: String,

    @SerializedName("phone")
    val phone: String

) : Parcelable