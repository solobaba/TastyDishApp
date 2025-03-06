package com.solobaba.tastydishapp.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.net.ConnectivityManager
import com.google.gson.Gson

fun logTrace(text: String) =  Log.d("Smartx", text)

//Json To Object
fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

//Object To Json String
fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

/**
 * Shows a short Toast with a String Parameter.
 */
fun Context.shortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.shortIntToast(msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

object NetworkUtils {
    @JvmStatic
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}

fun Context.showSnackBar(msg: String) {
}