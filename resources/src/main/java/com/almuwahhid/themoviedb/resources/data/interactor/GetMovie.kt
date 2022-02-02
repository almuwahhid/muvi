package com.almuwahhid.themoviedb.resources.data.interactor

import com.almuwahhid.themoviedb.resources.data.entity.Genre
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.repository.offline.OfflineMovieRepository
import com.almuwahhid.themoviedb.resources.data.repository.online.OnlineMovieRepository
import com.tokopedia.core.util.network.Common

class GetMovie(val onlinerepo : OnlineMovieRepository, val offlinerepo : OfflineMovieRepository) {
    suspend fun discover(page : Int) : Common<Pair<List<Movie>, Int>, Pair<Int, String>> = onlinerepo.discover(page)
    suspend fun discoverByGenre(genreId : Int, page : Int) : Common<Pair<List<Movie>, Int>, Pair<Int, String>> = onlinerepo.discoverByGenre(genreId, page)
    suspend fun popular() : Common<List<Movie>, Pair<Int, String>> = onlinerepo.popular()
    suspend fun detail(id : Int) : Common<Movie, Pair<Int, String>> = onlinerepo.detail(id)
    suspend fun genres() : Common<List<Genre>, Pair<Int, String>> = onlinerepo.genres()
    suspend fun search(key : String) : Common<List<Movie>, Pair<Int, String>> = onlinerepo.search(key)

    suspend fun addMovies(movies : List<Movie>) : List<Long> = offlinerepo.addMovies(movies)
    suspend fun remove() : Int = offlinerepo.remove()
    suspend fun getOffline() : List<Movie> = offlinerepo.get()
    suspend fun getOfflineByGenre(genreId : Int) : List<Movie> = offlinerepo.getByGenre(genreId)
}