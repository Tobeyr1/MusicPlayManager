package com.tobery.musicplay.util

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.ConnectivityManager
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.annotation.SuppressLint
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.RuntimeException

@RequiresApi(Build.VERSION_CODES.M)
internal fun NetWorkObserver(context: Context,listener: NetWorkObserver.Listener):NetWorkObserver{
    val connectivityManager : ConnectivityManager? = context.getSystemService()
    if (connectivityManager == null || !context.isPermissionGranted(ACCESS_NETWORK_STATE)){
        "Unable to register network observer.".printLog()
        return EmptyNetworkObserver()
    }
    return try {
        RealNetworkObserver(connectivityManager,listener)
    }catch (e: Exception){
        "${RuntimeException("Failed to register network observer.",e)}".printLog()
        EmptyNetworkObserver()
    }
}


internal fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
}
internal interface NetWorkObserver {

    val isOnline: Boolean

    fun shutdown()

    fun interface Listener{

        @MainThread
        fun onConnectivityChange(isOnline: Boolean)
    }

}

internal class EmptyNetworkObserver: NetWorkObserver{
    override val isOnline get() = true

    override fun shutdown() {}
}

@SuppressLint("MissingPermission")
@RequiresApi(Build.VERSION_CODES.M)
private class RealNetworkObserver(private val connectivityManager: ConnectivityManager,
                                  private val listener: NetWorkObserver.Listener)
    : NetWorkObserver{

    private val networkCallback = object : ConnectivityManager.NetworkCallback(){
        override fun onAvailable(network: Network) = onConnectivityChange(network,true)

        override fun onLost(network: Network)  = onConnectivityChange(network,false)
    }
    override val isOnline: Boolean
        get() = connectivityManager.activeNetwork!!.let { it.isOnline() }

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request,networkCallback)
    }

    override fun shutdown() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun onConnectivityChange(network: Network,isOnline: Boolean){

        val isAnyOnline = connectivityManager.activeNetwork.let {
            if (it == network){
                isOnline
            }else{
                it!!.isOnline()
            }
        }
        listener.onConnectivityChange(isAnyOnline)
    }

    private fun Network.isOnline():Boolean{
        val capabilities = connectivityManager.getNetworkCapabilities(this)
        return capabilities != null && capabilities.hasCapability(NET_CAPABILITY_INTERNET)
    }

}