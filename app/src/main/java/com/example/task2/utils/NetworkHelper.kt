package com.example.task2.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context:Context) {
//    fun isNetworkConnected() {
//        val result = false
//          val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkCapabilities = connectivityManager.activeNetwork ?: return false
//        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
//        return = when {
//            activeNetwork.hasCapability(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            activeNetwork.hasCapability(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
//            activeNetwork.hasCapability(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
//            else -> true
//        }
//
//    }
}