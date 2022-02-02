package com.almuwahhid.themoviedb.app.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.interactor.GetMovie
import com.almuwahhid.themoviedb.resources.util.base.BaseViewModel
import kotlinx.coroutines.launch

class MoviesViewModel(val getMovie: GetMovie) : BaseViewModel() {
    val movies: LiveData<Pair<List<Movie>, Boolean>> get() = _movies
    private val _movies = MutableLiveData<Pair<List<Movie>, Boolean>>()

    private var offset = 1

    fun requiredRefresh(after : () -> Unit) {
        offset = 1
        after()
    }

    fun getMovies(isInternetAvailable : Boolean) {
            if(isInternetAvailable) {
                viewModelScope.launch {
                    if(offset == 1) _loading.postValue(true)
                    getMovie.discover(offset).fold(
                        fnL = {
                            if(offset == 1) _loading.postValue(false)
                            getMovie.addMovies(it.first).also { res ->
                                _movies.postValue(Pair(it.first, offset == 1))
                                offset = it.second+1
                            }

                        },
                        fnR = {
                            if(offset == 1) _loading.postValue(false)
                            _error_message.postValue(it.second)
                        }
                    )
                }
            } else {
                if(offset == 1) {
                    viewModelScope.launch {
                        getMovie.getOffline().let {
                            _movies.postValue(Pair(it, true))
                            offset++
                        }
                    }
                }
            }
    }

    fun getMoviesByGenre(genreId : Int, isInternetAvailable : Boolean) {
        if(isInternetAvailable) {
            viewModelScope.launch {
                if(offset == 1) _loading.postValue(true)
                getMovie.discoverByGenre(genreId, offset).fold(
                    fnL = {
                        if(offset == 1) _loading.postValue(false)
                        getMovie.addMovies(it.first).also { res ->
                            _movies.postValue(Pair(it.first, offset == 1))
                            offset = it.second+1
                        }
                    },
                    fnR = {
                        if(offset == 1) _loading.postValue(false)
                        _error_message.postValue(it.second)
                    }
                )
            }
        } else {
            if(offset == 1) {
                viewModelScope.launch {
                    getMovie.getOfflineByGenre(genreId).let {
                        _movies.postValue(Pair(it, true))
                        offset++
                    }
                }
            }
        }
    }
}