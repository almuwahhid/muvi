package com.almuwahhid.themoviedb.resources.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun get(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(movies : List<Movie>) : List<Long>

    @Query("DELETE FROM Movie")
    fun remove() : Int
}