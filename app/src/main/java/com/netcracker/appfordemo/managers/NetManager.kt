package com.netcracker.appfordemo.managers

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by mozil on 19.01.2018.
 */

class NetManager(private var applicationContext: Context) {

    val isConnectedToInternet: Boolean?
        get() {

            val conManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            val ni = conManager.activeNetworkInfo
            return ni != null && ni.isConnected
        }
}