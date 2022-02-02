package com.almuwahhid.themoviedb.resources.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.source.local.db.converter.MovieConverters
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.FavMovieDao
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.MovieDao

@Database(
        entities = [FavMovie::class, Movie::class],
        version = 5, exportSchema = false
)

@TypeConverters(
    MovieConverters::class
)
abstract class MovieDB : RoomDatabase() {
    abstract val favMovieDao: FavMovieDao
    abstract val movieDao: MovieDao
}