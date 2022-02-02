package com.almuwahhid.themoviedb.resources.data.interactor

import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.repository.offline.OfflineFavMovieRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMovie(val offlineFavMovieRepository: OfflineFavMovieRepository) {
    suspend fun addFavoriteMovie(movie: FavMovie) : Long = offlineFavMovieRepository.addFavoriteMovie(movie)
    suspend fun removeFavoriteMovie(movie: FavMovie) : Int = offlineFavMovieRepository.removeFavoriteMovie(movie)
    suspend fun getFavoriteMovies() : List<FavMovie> = offlineFavMovieRepository.getFavoriteMovies()
    suspend fun getFavoriteMoviesByGenre(id: Int) : List<FavMovie> = offlineFavMovieRepository.getFavoriteMoviesByGenre(id)
    suspend fun isMovieFavorited(id : Int) : Boolean = offlineFavMovieRepository.isMovieFavorited(id)
    suspend fun isMoviesFavorited(movies : List<FavMovie>) : Flow<Pair<FavMovie, Boolean>> = offlineFavMovieRepository.isMoviesFavorited(movies)
}