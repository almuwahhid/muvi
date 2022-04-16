package com.almuwahhid.themoviedb.di

import org.koin.dsl.module
import okhttp3.mockwebserver.MockWebServer

val MockWebServerDITest = module {
    factory {
        MockWebServer()
    }
}