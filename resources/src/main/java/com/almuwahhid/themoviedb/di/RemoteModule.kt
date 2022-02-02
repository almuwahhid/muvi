package com.almuwahhid.themoviedb.di

import com.almuwahhid.themoviedb.resources.data.source.remote.APIManager
import com.almuwahhid.themoviedb.resources.util.network.NetworkUtil
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val remoteModule = module {
    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        NetworkUtil.okHttpClient(androidContext())
    }

    single {
        NetworkUtil.retrofit(get(), get())
    }

    single {
        APIManager.provideMovie(get())
    }

}