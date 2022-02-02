package com.almuwahhid.themoviedb.resources.util.network

import com.google.gson.annotations.SerializedName

data class ListResult<out T>(
    @SerializedName("page")
    val page: Int = 1,
    @SerializedName("results")
    val results: T? = null
)