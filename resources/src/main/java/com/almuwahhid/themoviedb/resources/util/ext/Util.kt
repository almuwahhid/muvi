package com.almuwahhid.themoviedb.resources.util.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.almuwahhid.themoviedb.resources.R
import com.almuwahhid.themoviedb.resources.widget.BounceEffect
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.toData(): T {
    return Gson().fromJson(this, object: TypeToken<T>(){}.type)
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}

fun View.bounceEffect(context: Context){
    var anim = AnimationUtils.loadAnimation(context, R.anim.bounce_in)
    anim.setInterpolator(BounceEffect(0.2, 20.0))
    startAnimation(anim)
}

fun Context.ToastShort(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

@SuppressLint("MissingPermission")
fun Context.isInternetAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}