package com.almuwahhid.themoviedb.resources.data.repository.online

import com.almuwahhid.themoviedb.resources.data.entity.Genre
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.data.source.remote.api.MovieAPI
import com.almuwahhid.themoviedb.resources.util.network.parse
import com.tokopedia.core.util.network.Common
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OnlineMovieRepository(val movieAPI: MovieAPI) : IOnlinePokeRepository {
    override suspend fun discover(page: Int): Common<Pair<List<Movie>, Int>, Pair<Int, String>> = withContext(Dispatchers.IO) {
        return@withContext movieAPI.discover(page = page).parse().fold(
            fnL = {
                Common.Left(Pair(it.results?: arrayListOf(), it.page))
            },
            fnR = {
                Common.Right(it)
            }
        )
    }

    override suspend fun search(key: String): Common<List<Movie>, Pair<Int, String>> = withContext(Dispatchers.IO) {
        return@withContext movieAPI.search(key).parse().fold(
            fnL = {
                Common.Left(it.results?: arrayListOf())
            },
            fnR = {
                Common.Right(it)
            }
        )
    }

    override suspend fun discoverByGenre(
        genreId: Int,
        page: Int
    ): Common<Pair<List<Movie>, Int>, Pair<Int, String>> = withContext(Dispatchers.IO) {
        return@withContext movieAPI.discoverByGenre(with_genres = genreId, page = page).parse().fold(
            fnL = {
                Common.Left(Pair(it.results?: arrayListOf(), it.page))
            },
            fnR = {
                Common.Right(it)
            }
        )
    }

    override suspend fun popular(): Common<List<Movie>, Pair<Int, String>> = withContext(Dispatchers.IO) {
        return@withContext movieAPI.popular().parse().fold(
            fnL = {
                Common.Left(it.results?: arrayListOf())
            },
            fnR = {
                Common.Right(it)
            }
        )
    }

    override suspend fun detail(id: Int): Common<Movie, Pair<Int, String>> = withContext(Dispatchers.IO) {
        return@withContext movieAPI.detail(id).parse().fold(
            fnL = {
                Common.Left(it)
            },
            fnR = {
                Common.Right(it)
            }
        )
    }

    override suspend fun genres(): Common<List<Genre>, Pair<Int, String>> = withContext(Dispatchers.IO) {
        return@withContext movieAPI.genre().parse().fold(
            fnL = {
                Common.Left(it.genres!!)
            },
            fnR = {
                Common.Right(it)
            }
        )
    }
}


interface IOnlinePokeRepository{
    suspend fun discover(page : Int) : Common<Pair<List<Movie>, Int>, Pair<Int, String>>
    suspend fun search(key : String) : Common<List<Movie>, Pair<Int, String>>
    suspend fun discoverByGenre(genreId : Int, page : Int) : Common<Pair<List<Movie>, Int>, Pair<Int, String>>
    suspend fun popular() : Common<List<Movie>, Pair<Int, String>>
    suspend fun detail(id : Int) : Common<Movie, Pair<Int, String>>
    suspend fun genres() : Common<List<Genre>, Pair<Int, String>>
}