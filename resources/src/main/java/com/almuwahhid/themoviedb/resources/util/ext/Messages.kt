package com.almuwahhid.themoviedb.resources.util.ext

import android.util.Log

fun Int.getMessage() : String{
    Log.d("errorhttp", "error $this")
    when(this) {
        500 -> return "Server sedang bermasalah, coba beberapa saat lagi"
        404 -> return "Internet Anda sedang bermasalah, silahkan cek kembali"
        422 -> return "Koneksi internet Anda bermasalah, silahkan coba beberapa saat lagi"
        400 -> return "Ada masalah dengan API, coba beberapa saat lagi"
    }

    return "Sedang ada masalah dengan API, silahkan coba lagi beberapa saat lagi"
}