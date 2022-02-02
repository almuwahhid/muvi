package com.almuwahhid.themoviedb.resources.data.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class FavMovie (
    @SerializedName("flag")
    var flag: Boolean = false
) : Movie()