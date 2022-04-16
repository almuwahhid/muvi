package com.almuwahhid.themoviedb.di

import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.source.remote.api.MovieAPI
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

fun configureNetworkModuleForTest(baseApi : String) = module{
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory {
        get<Retrofit>().create(MovieAPI::class.java)
    }
}