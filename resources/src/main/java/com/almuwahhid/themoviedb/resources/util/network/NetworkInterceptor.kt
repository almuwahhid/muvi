package com.almuwahhid.themoviedb.resources.util.network


import android.content.Context
import com.almuwahhid.themoviedb.resources.R
import com.almuwahhid.themoviedb.resources.util.ext.isInternetAvailable
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isInternetAvailable()) {
            return createResponse(422, "Tidak ada koneksi", chain)
        }
        return try {
            val response = chain.proceed(chain.request())
            val type = response.body?.contentType()?.subtype
//            check(type == "json") { "Ada yang bermasalah dengan server, coba lagi nanti" }
            check(type == "json") { "" }
            response
        } catch (e: Exception) {
            createResponse(500, "Server bermasalah", chain)
        }
    }

    private fun createResponse(
        statusCode: Int,
        message: String,
        chain: Interceptor.Chain
    ): Response {
        val type = "application/json; charset=utf-8".toMediaTypeOrNull()
        return Response.Builder()
            .protocol(Protocol.HTTP_1_1)
            .code(statusCode)
            .message("network error")
            .body(
                message.toResponseBody(type)
            )
            .request(chain.request())
            .build()
    }
}