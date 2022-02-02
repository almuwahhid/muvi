package com.almuwahhid.themoviedb.resources.data.source.local.db

import android.app.Application
import androidx.room.Room
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.FavMovieDao
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.MovieDao

object DbManager {
    fun provideDatabase(application: Application): MovieDB {
        return Room.databaseBuilder(application, MovieDB::class.java, "muvi")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideFavMovie(database: MovieDB): FavMovieDao {
        return  database.favMovieDao
    }
    fun provideMovie(database: MovieDB): MovieDao {
        return  database.movieDao
    }
}