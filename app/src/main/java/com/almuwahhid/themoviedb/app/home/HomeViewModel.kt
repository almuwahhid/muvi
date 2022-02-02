package com.almuwahhid.themoviedb.app.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.data.entity.Genre
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.interactor.GetFavoriteMovie
import com.almuwahhid.themoviedb.resources.data.interactor.GetMovie
import com.almuwahhid.themoviedb.resources.data.mapper.toFavorite
import com.almuwahhid.themoviedb.resources.util.base.BaseViewModel
import com.almuwahhid.themoviedb.resources.util.ext.toJson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect

class HomeViewModel(val getFavoriteMovie: GetFavoriteMovie, val getMovie: GetMovie) : BaseViewModel() {
    val populars: LiveData<List<FavMovie>> get() = _populars
    private val _populars = MutableLiveData<List<FavMovie>>()

    val genres: LiveData<List<Genre>> get() = _genres
    private val _genres = MutableLiveData<List<Genre>>()

//    val async: LiveData<String> get() = _async
//    private val _async = MutableLiveData<String>()

    val async: StateFlow<String> get() = _async
    private val _async = MutableStateFlow("")

    val update_genre: LiveData<GENRE_BY_TYPE> get() = _update_genre
    private val _update_genre = MutableLiveData<GENRE_BY_TYPE>()

    val favorited: LiveData<FavMovie> get() = _favorited
    private val _favorited = MutableLiveData<FavMovie>()

    val refresh_favorited: LiveData<Boolean> get() = _refresh_favorited
    private val _refresh_favorited = MutableLiveData<Boolean>()

    fun refreshMovieByGenre(type : GENRE_BY_TYPE) {
        _update_genre.postValue(type)
    }

    fun sampleAsync1(s : String) {
        viewModelScope.launch {
//            _async.value = async { sinkronous(s) }.await()
//            _async.value = async { sinkronous("$s $s") }.await()
//            _async.value = async { sinkronous("$s $s $s") }.await()
//            _async.value = async { sinkronous("$s $s $s $s") }.await()
//            _async.value = async { sinkronous("$s $s $s $s $s") }.await()

//            _async.value = sinkronous(s)
//            _async.value = sinkronous("$s $s")
            _async.emit(sinkronous("$s $s"))
//            _async.value = sinkronous("$s $s $s")
//            _async.value = sinkronous("$s $s $s $s")
//            _async.value = sinkronous("$s $s $s $s $s")
        }
    }

    private suspend fun sinkronous(s : String) = withContext(Dispatchers.Main) {
        delay(3000)
        Log.d("async1", "run here $s")
        return@withContext s
    }

    fun removeOrAdd(favMovie: FavMovie, favorited : Boolean) {
        viewModelScope.launch {
            delay(500)
            if(favorited) {
                getFavoriteMovie.addFavoriteMovie(favMovie).also {
                    favMovie.flag = favorited
                    _favorited.postValue(favMovie)
                    _refresh_favorited.postValue(true)
                }
            } else {
                getFavoriteMovie.removeFavoriteMovie(favMovie).also {
                    favMovie.flag = favorited
                    _favorited.postValue(favMovie)
                    _refresh_favorited.postValue(true)
                }
            }
        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            _loading.postValue(true)
            getMovie.popular().fold(
                fnL = {
                    _loading.postValue(false)
                    _populars.postValue(it.toFavorite())
                    delay(1000)
                    checkFavoriteMovies(it.toFavorite())
                },
                fnR = {
                    _error_message.postValue(it.second)
                }
            )
        }
    }

    fun getGenres() {
        viewModelScope.launch {
            getMovie.genres().fold(
                fnL = {
                    _genres.postValue(it)
                },
                fnR = {
                    _error_message.postValue(it.second)
                }
            )
        }
    }

    private fun checkFavoriteMovies(list : List<FavMovie>) {
        viewModelScope.launch {
            getFavoriteMovie.isMoviesFavorited(list)
                .buffer()
                .collect {
                    Log.d("favMovieUpdate", "${it.second} ${it.first.toJson()}")
                    if(it.second) {
                        it.first.flag = true
                        _favorited.postValue(it.first)
                    }
                }
        }
    }
}

sealed class GENRE_BY_TYPE {
    object ALL : GENRE_BY_TYPE()
    data class SPECIFIC(val genreId : Int = 0) : GENRE_BY_TYPE()
}