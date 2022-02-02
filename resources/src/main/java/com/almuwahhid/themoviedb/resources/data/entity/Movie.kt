package com.almuwahhid.themoviedb.resources.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
open class Movie(
    @PrimaryKey(autoGenerate = false) @SerializedName("id")
    open var id: Int = 0,
    @SerializedName("original_title")
    open var original_title: String = "",
    @SerializedName("homepage")
    open var homepage: String = "",
    @SerializedName("overview")
    open var overview: String? = null,
    @SerializedName("vote_average")
    open var vote_average: Double = 0.0,
    @SerializedName("belongs_to_collection")
    open var belongs_to_collection: Collection? = null,
    @SerializedName("genre_ids")
    open var genre_ids: List<Int> = arrayListOf(),
    @SerializedName("poster_path")
    open var poster_path: String? = null,
    @SerializedName("backdrop_path")
    open var backdrop_path: String? = null
) {
    data class Collection(
        @SerializedName("poster_path")
        val poster_path: String? = null,
        @SerializedName("backdrop_path")
        val backdrop_path: String? = null,
    )
}