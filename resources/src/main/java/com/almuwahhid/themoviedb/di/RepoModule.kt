package com.almuwahhid.themoviedb.di

import com.almuwahhid.themoviedb.resources.data.repository.offline.OfflineFavMovieRepository
import com.almuwahhid.themoviedb.resources.data.repository.offline.OfflineMovieRepository
import com.almuwahhid.themoviedb.resources.data.repository.online.OnlineMovieRepository
import org.koin.dsl.module

val repoModule = module {
    single {
        OnlineMovieRepository(get())
    }
    single {
        OfflineMovieRepository(get())
    }
    single {
        OfflineFavMovieRepository(get())
    }
}