package com.almuwahhid.themoviedb.resources.data.source.remote.api

import com.almuwahhid.themoviedb.resources.data.entity.Genre
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.domain.GenreResult
import com.almuwahhid.themoviedb.resources.util.network.ListResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface MovieAPI {
    @GET("discover/movie")
    suspend fun discover(
        @Query("page") page: Int = 1
    ): Response<ListResult<List<Movie>>>

    @GET("discover/movie")
    suspend fun discoverByGenre(
        @Query("with_genres") with_genres: Int = 1,
        @Query("page") page: Int = 1
    ): Response<ListResult<List<Movie>>>

    @GET("movie/popular")
    suspend fun popular(): Response<ListResult<List<Movie>>>

    @GET("genre/movie/list")
    suspend fun genre(): Response<GenreResult<List<Genre>>>

    @GET("movie/{id}")
    suspend fun detail(
        @Path("id") id: Int,
    ): Response<Movie>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String,
    ): Response<ListResult<List<Movie>>>
}

