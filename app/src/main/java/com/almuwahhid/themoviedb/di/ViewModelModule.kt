package com.almuwahhid.themoviedb.di

import com.almuwahhid.themoviedb.app.detail.MovieDetailViewModel
import com.almuwahhid.themoviedb.app.favorite.FavMoviesViewModel
import com.almuwahhid.themoviedb.app.home.HomeViewModel
import com.almuwahhid.themoviedb.app.movies.MoviesViewModel
import com.almuwahhid.themoviedb.app.search.SearchMovieViewModel
import com.almuwahhid.themoviedb.resources.util.base.BaseViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        BaseViewModel()
    }

    viewModel {
        HomeViewModel(
            getMovie = get(),
            getFavoriteMovie = get(),
        )
    }

    viewModel {
        FavMoviesViewModel(
            getFavoriteMovie = get()
        )
    }

    viewModel {
        MoviesViewModel(
            getMovie = get()
        )
    }

    viewModel {
        MovieDetailViewModel(
            getMovie = get(),
            getFavoriteMovie = get(),
        )
    }
    viewModel {
        SearchMovieViewModel(
            getMovie = get()
        )
    }

}