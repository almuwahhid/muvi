package com.almuwahhid.themoviedb.resources.domain

import com.google.gson.annotations.SerializedName

data class GenreResult <out T>(
    @SerializedName("genres")
    val genres: T? = null
)