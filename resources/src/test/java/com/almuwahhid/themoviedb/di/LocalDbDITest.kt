package com.almuwahhid.themoviedb.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.source.local.db.MovieDB
import com.almuwahhid.themoviedb.resources.data.source.remote.api.MovieAPI
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

fun configureLocalDbModuleForTest() = module{
    single {
        Room.databaseBuilder(ApplicationProvider.getApplicationContext<Context>(), MovieDB::class.java, "muvi")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory {
        get<MovieDB>().movieDao
    }
    factory {
        get<MovieDB>().favMovieDao
    }
}