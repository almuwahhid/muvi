package com.almuwahhid.themoviedb.resources.data.source.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie

@Dao
interface FavMovieDao {
    @Query("SELECT * FROM FavMovie")
    fun get(): List<FavMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(favMovie: FavMovie) : Long

    @Query("DELETE FROM FavMovie")
    fun removeAll()

    @Query("DELETE FROM FavMovie WHERE id = :id")
    fun remove(id : Int) : Int

    @Query("SELECT EXISTS(SELECT * FROM FavMovie WHERE id = :id)")
    fun isExist(id : Int) : Boolean
}