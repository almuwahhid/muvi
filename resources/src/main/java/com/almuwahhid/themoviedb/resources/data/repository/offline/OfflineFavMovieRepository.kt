package com.almuwahhid.themoviedb.resources.data.repository.offline

import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.source.local.db.dao.FavMovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class OfflineFavMovieRepository(val favMovie : FavMovieDao) : IOfflineFavMovieRepository{

    override suspend fun addFavoriteMovie(movie: FavMovie): Long = withContext(Dispatchers.IO) {
        return@withContext favMovie.add(movie)
    }

    override suspend fun removeFavoriteMovie(movie: FavMovie): Int  = withContext(Dispatchers.IO) {
        return@withContext favMovie.remove(movie.id)
    }

    override suspend fun getFavoriteMovies(): List<FavMovie>  = withContext(Dispatchers.IO) {
        return@withContext favMovie.get()
    }

    override suspend fun getFavoriteMoviesByGenre(genreId: Int): List<FavMovie> = withContext(Dispatchers.IO) {
        return@withContext favMovie.get().filter {
            it.genre_ids.contains(genreId)
        }
    }

    override suspend fun isMovieFavorited(id: Int): Boolean = withContext(Dispatchers.IO) {
        return@withContext favMovie.isExist(id)
    }

    override suspend fun isMoviesFavorited(movies : List<FavMovie>): Flow<Pair<FavMovie, Boolean>> {
        return flow {
            for(movie in movies) {
                emit(Pair(movie, favMovie.isExist(movie.id)))
                kotlinx.coroutines.delay(200)
            }
        }.flowOn(Dispatchers.IO)
    }
}

interface IOfflineFavMovieRepository{
    suspend fun addFavoriteMovie(movie: FavMovie) : Long
    suspend fun removeFavoriteMovie(movie: FavMovie) : Int
    suspend fun getFavoriteMovies() : List<FavMovie>
    suspend fun getFavoriteMoviesByGenre(genreId : Int) : List<FavMovie>
    suspend fun isMovieFavorited(id : Int) : Boolean
    suspend fun isMoviesFavorited(movies : List<FavMovie>) : Flow<Pair<FavMovie, Boolean>>
}