package com.almuwahhid.themoviedb.resources.data.source.remote

import com.almuwahhid.themoviedb.resources.data.source.remote.api.MovieAPI
import retrofit2.Retrofit

object APIManager {
    fun provideMovie(retrofit: Retrofit): MovieAPI {
        return retrofit.create(MovieAPI::class.java)
    }
}