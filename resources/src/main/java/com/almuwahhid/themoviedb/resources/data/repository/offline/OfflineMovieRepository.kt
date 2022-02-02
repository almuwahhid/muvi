package com.almuwahhid.themoviedb.resources.data.repository.offline

import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.FavMovieDao
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineMovieRepository(val db : MovieDao) : IOfflineMovieRepository{

    override suspend fun addMovies(movies: List<Movie>): List<Long> = withContext(Dispatchers.IO) {
        return@withContext db.add(movies)
    }

    override suspend fun remove(): Int  = withContext(Dispatchers.IO) {
        return@withContext db.remove()
    }

    override suspend fun get(): List<Movie>  = withContext(Dispatchers.IO) {
        return@withContext db.get()
    }

    override suspend fun getByGenre(genreId: Int): List<Movie> = withContext(Dispatchers.IO) {
        return@withContext db.get().filter {
            it.genre_ids.contains(genreId)
        }
    }

}

interface IOfflineMovieRepository{
    suspend fun addMovies(movies : List<Movie>) : List<Long>
    suspend fun remove() : Int
    suspend fun get() : List<Movie>
    suspend fun getByGenre(genreId : Int) : List<Movie>
}