package com.abhishekn.techm.network

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

object Utility {

    // Condition to check the network connectivity
    @SuppressLint("ServiceCast")
    fun isNetworkAvailable(activity: FragmentActivity?):Boolean{
        activity?.let {
            val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
        return false

    }
}