package com.almuwahhid.themoviedb.app.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.interactor.GetFavoriteMovie
import com.almuwahhid.themoviedb.resources.util.base.BaseViewModel
import kotlinx.coroutines.launch

class FavMoviesViewModel(val getFavoriteMovie: GetFavoriteMovie) : BaseViewModel() {

    val favmonsters: LiveData<List<FavMovie>> get() = _favmonsters
    private val _favmonsters = MutableLiveData<List<FavMovie>>()

    fun getFavoriteMovie() {
        viewModelScope.launch {
            _loading.postValue(true)
            getFavoriteMovie.getFavoriteMovies().let {
                _loading.postValue(false)
                _favmonsters.postValue(it)
            }
        }
    }

    fun getFavoriteMovieByGenre(idgenre : Int) {
        viewModelScope.launch {
            _loading.postValue(true)
            getFavoriteMovie.getFavoriteMoviesByGenre(idgenre).let {
                _loading.postValue(false)
                _favmonsters.postValue(it)
            }
        }
    }
}