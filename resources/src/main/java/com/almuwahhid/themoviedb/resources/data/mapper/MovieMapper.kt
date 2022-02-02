package com.almuwahhid.themoviedb.resources.data.mapper

import android.net.Uri
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie

fun Movie.toFavorite() : FavMovie {
    return FavMovie().apply {
        id = this@toFavorite.id
        original_title = this@toFavorite.original_title
        homepage = this@toFavorite.homepage
        overview = this@toFavorite.overview
        vote_average = this@toFavorite.vote_average
        belongs_to_collection = this@toFavorite.belongs_to_collection
        poster_path = this@toFavorite.poster_path
        backdrop_path = this@toFavorite.backdrop_path
        genre_ids = this@toFavorite.genre_ids
    }
}

fun List<Movie>.toFavorite() : List<FavMovie> {
    var res = arrayListOf<FavMovie>()
    for(movie in this) {
        res.add(FavMovie().apply {
            id = movie.id
            original_title = movie.original_title
            homepage = movie.homepage
            overview = movie.overview
            vote_average = movie.vote_average
            belongs_to_collection = movie.belongs_to_collection
            poster_path = movie.poster_path
            backdrop_path = movie.backdrop_path
            genre_ids = movie.genre_ids
        })
    }
    return res
}


fun Movie.Collection.toListedAvatar() : MutableList<String> {
    val res = ArrayList<String>()
    poster_path?.let {
        res.add(it)
    }
    backdrop_path?.let {
        res.add(it)
    }
    return res
}

fun Movie.toListedAvatar() : MutableList<String> {
    val res = ArrayList<String>()
    poster_path?.let {
        res.add(it)
    }
    backdrop_path?.let {
        res.add(it)
    }
    return res
}