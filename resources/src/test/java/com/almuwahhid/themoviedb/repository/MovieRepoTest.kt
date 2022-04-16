package com.almuwahhid.themoviedb.repository

import com.almuwahhid.themoviedb.base.BaseMuviTest
import com.almuwahhid.themoviedb.resources.data.repository.online.OnlineMovieRepository
import com.almuwahhid.themoviedb.resources.data.source.remote.api.MovieAPI
import org.koin.test.inject
import okhttp3.mockwebserver.MockWebServer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.almuwahhid.themoviedb.di.configureResourceTestComponent
import com.almuwahhid.themoviedb.resources.data.repository.offline.OfflineMovieRepository
import com.almuwahhid.themoviedb.resources.data.source.local.db.MovieDB
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import java.net.HttpURLConnection

class MovieRepoTest : BaseMuviTest() {

    val mockWebServer : MockWebServer by inject()

    val mRepo : OnlineMovieRepository by inject()

    val mOfflineRepo : OfflineMovieRepository by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun start(){
        super.setUp()

        startKoin{ modules(configureResourceTestComponent(getMockWebServerUrl()))}
    }

    @Test
//    fun `test_login_repo_retrieves_expected_data`() =  runBlocking<Unit>{
    fun `test_login_repo_retrieves_expected_data`() =  runTest {
        mockNetworkResponseWithFileContent("movies_list.json", HttpURLConnection.HTTP_OK)
        val dataReceived = mRepo.discover(1)
        assertNotNull(dataReceived)
        assertEquals(dataReceived.isLeft, true)
    }

    @Test
    fun `load_and_save_tolocal`() =  runTest {
        mockNetworkResponseWithFileContent("movies_list.json", HttpURLConnection.HTTP_OK)
        val dataReceived = mRepo.discover(1)
        dataReceived.fold(
            fnL = {
                mOfflineRepo.addMovies(it.first)
                val datas = mOfflineRepo.get()
                assertEquals(it.first.size, datas.size)
            },
            fnR = {

            }
        )
    }


}