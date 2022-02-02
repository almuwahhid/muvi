package com.almuwahhid.themoviedb.resources.data.source.local.db.converter

import androidx.room.TypeConverter
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieConverters {
    @TypeConverter
    fun stringToFavMovie(json: String): FavMovie? {
        val gson = Gson()
        val type = object : TypeToken<FavMovie>() {}.type
        return gson.fromJson(json, type)
    }
    @TypeConverter
    fun stringToMovie(json: String): Movie? {
        val gson = Gson()
        val type = object : TypeToken<Movie>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun stringToIntList(json: String): List<Int> {
        val gson = Gson()
        val type = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun listIntToString(list : List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToCollection(json: String): Movie.Collection {
        if(!json.isEmpty()) {
            val gson = Gson()
            val type = object : TypeToken<Movie.Collection>() {}.type
            return gson.fromJson(json, type)
        }
        return Movie.Collection()
    }

    @TypeConverter
    fun collectionToString(collection: Movie.Collection?): String {
        collection?.let {
            return Gson().toJson(it)
        }
        return ""
    }
}