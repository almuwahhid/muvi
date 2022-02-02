package com.almuwahhid.themoviedb

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.almuwahhid.themoviedb.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        @JvmStatic
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidContext(this@App)
            // TODO : Rules = View -> ViewModel -> Repo -> Source(Offline/Online)
            modules(
                listOf(
                    remoteModule,
                    databaseModule,
                    repoModule,
                    interactorModule,
                    viewModelModule,
                )
            )
        }
//        Timber.plant(DebugTree("_poketest"))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}