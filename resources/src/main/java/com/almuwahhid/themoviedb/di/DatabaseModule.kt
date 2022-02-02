package com.almuwahhid.themoviedb.di

import com.almuwahhid.themoviedb.resources.data.source.local.db.DbManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single { DbManager.provideDatabase(androidApplication()) }
    single { DbManager.provideFavMovie(get()) }
    single { DbManager.provideMovie(get()) }
}
