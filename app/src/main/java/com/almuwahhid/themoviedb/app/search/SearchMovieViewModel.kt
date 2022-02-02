package com.almuwahhid.themoviedb.app.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.interactor.GetMovie
import com.almuwahhid.themoviedb.resources.util.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchMovieViewModel(val getMovie: GetMovie) : BaseViewModel() {
    val keysearch: LiveData<String> get() = _keysearch
    private val _keysearch = MutableLiveData<String>()

    val movies: LiveData<List<Movie>> get() = _movies
    private val _movies = MutableLiveData<List<Movie>>()

    private var searchJob: Job? = null

    fun searchMovies(key : String) {
        viewModelScope.launch {
            _loading.postValue(true)
            getMovie.search(key).fold(
                fnL = {
                    _loading.postValue(false)
                    _movies.postValue(it)
                },
                fnR = {
                    _loading.postValue(false)
                    _error_message.postValue(it.second)
                }
            )
        }
    }

    fun debounceSearch(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            _keysearch.postValue(searchText)
        }
    }
}