package com.anatame.flordia.utils.server.plugins.dns_resolver

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.anatame.flordia.FlordiaApp
import com.anatame.flordia.data.network.AppNetworkClient
import com.anatame.flordia.utils.readList
import com.anatame.flordia.utils.writeList
import com.google.gson.Gson
import java.net.InetAddress


object DnsResolver {
    private val TAG = "DnsResolverStatus"
    private val gson = Gson()
    private val sharedPref: SharedPreferences = FlordiaApp.getApplicationContext().getSharedPreferences("dnscache", Context.MODE_PRIVATE)

    operator fun invoke(host: String): List<InetAddress> {
        val cacheAddr = readCache(host)
        return if(cacheAddr.isEmpty()){
            Log.d(TAG, "Reading DNS address from network for $host")
            val addr = AppNetworkClient.getClient().dns.lookup(host)
            createCache(host, addr)
            addr
        } else {
            Log.d(TAG, "Reading DNS address from disk for $host")
            cacheAddr
        }
    }

    fun createCache(host: String, addressList: List<InetAddress>){
        sharedPref.writeList(gson, host, addressList)
    }

    fun readCache(host: String): List<InetAddress> {
        return sharedPref.readList(gson, host)
    }
}

