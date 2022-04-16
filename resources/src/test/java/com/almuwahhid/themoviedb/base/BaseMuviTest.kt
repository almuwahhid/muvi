package com.almuwahhid.themoviedb.base

import org.koin.test.KoinTest
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import java.io.File

open class BaseMuviTest : KoinTest {
    private lateinit var mockServerInstance : MockWebServer
    private var mShouldStart = false

    @Before
    open fun setUp(){
        startMockServer(true)
    }

    /**
     * Helps to read input file returns the respective data in mocked call
     */
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    /**
     * Reads input file and converts to json
     */
    fun getJson(path : String) : String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    @After
    open fun tearDown(){
        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }

    private fun startMockServer(shouldStart:Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mockServerInstance = MockWebServer()
            mockServerInstance.start()
        }
    }

    fun getMockWebServerUrl() = mockServerInstance.url("/").toString()

    private fun stopMockServer() {
        if (mShouldStart){
            mockServerInstance.shutdown()
        }
    }

}