package com.almuwahhid.themoviedb.di

fun configureResourceTestComponent(baseApi : String) = listOf(
    MockWebServerDITest,
    configureNetworkModuleForTest(baseApi),
    configureLocalDbModuleForTest(),
    repoModule,
    interactorModule
)