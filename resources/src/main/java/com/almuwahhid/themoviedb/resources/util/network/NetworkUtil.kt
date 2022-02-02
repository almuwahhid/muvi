package com.almuwahhid.themoviedb.resources.util.network

import android.content.Context
import com.almuwahhid.themoviedb.resources.GlobalConfig.API_KEY
import com.almuwahhid.themoviedb.resources.GlobalConfig.HOST_API
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetworkUtil {
    fun okHttpClient(context: Context) : OkHttpClient{
        val client = OkHttpClient.Builder()
        client.addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
        client.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
        client.addInterceptor(TokenInterceptor())
        client.addInterceptor(NetworkInterceptor(context))
        client.connectTimeout(30, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.writeTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }
    fun retrofit(okHttpClient: OkHttpClient, gson : Gson) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(HOST_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }
}