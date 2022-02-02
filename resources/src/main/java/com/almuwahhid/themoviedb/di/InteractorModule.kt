package com.almuwahhid.themoviedb.di

import com.almuwahhid.themoviedb.resources.data.interactor.GetFavoriteMovie
import com.almuwahhid.themoviedb.resources.data.interactor.GetMovie
import org.koin.dsl.module

val interactorModule = module {
    single {
        GetMovie(
                onlinerepo = get(),
                offlinerepo = get())
    }
    single {
        GetFavoriteMovie(get())
    }
}