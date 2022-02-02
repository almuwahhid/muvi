package com.almuwahhid.themoviedb.app.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.interactor.GetFavoriteMovie
import com.almuwahhid.themoviedb.resources.data.interactor.GetMovie
import com.almuwahhid.themoviedb.resources.data.mapper.toFavorite
import com.almuwahhid.themoviedb.resources.util.base.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MovieDetailViewModel(val getMovie: GetMovie, val getFavoriteMovie: GetFavoriteMovie) : BaseViewModel() {
    val movie: LiveData<FavMovie> get() = _movie
    private val _movie = MutableLiveData<FavMovie>()

    val favorited: LiveData<Boolean> get() = _favorited
    private val _favorited = MutableLiveData<Boolean>()

    fun removeOrAdd(favMovie : FavMovie, favorited : Boolean) {
        viewModelScope.launch {
            if(favorited) {
                getFavoriteMovie.addFavoriteMovie(favMovie).also {
                    Log.d("fav", "fav $it")
                    isMovieFavorited(favMovie.id)
                }
            } else {
                getFavoriteMovie.removeFavoriteMovie(favMovie).also {
                    Log.d("fav-remove", "fav $it")
                    isMovieFavorited(favMovie.id)
                }
            }
        }
    }

    fun getDetailMovie(id : Int) {
        viewModelScope.launch {
            _loading.postValue(true)
            getMovie.detail(id).fold(
                fnL = {
                    _loading.postValue(false)
                    _movie.postValue(it.toFavorite())
                },
                fnR = {
                    _loading.postValue(false)
                    _error_message.postValue(it.second)
                }
            )
        }
    }

    fun isMovieFavorited(id : Int) {
        viewModelScope.launch {
            getFavoriteMovie.isMovieFavorited(id).let {
                _favorited.postValue(it)
            }
        }
    }
}