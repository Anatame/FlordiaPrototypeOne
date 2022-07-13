package com.anatame.flordia.utils.server.plugins.dns_resolver

import android.util.Log
import android.util.Patterns
import com.anatame.flordia.data.network.AppNetworkClient
import com.anatame.flordia.utils.customElapsedTime
import io.github.krlvm.powertunnel.sdk.proxy.DNSRequest
import io.github.krlvm.powertunnel.sdk.proxy.ProxyAdapter
import java.net.InetAddress
import java.net.InetSocketAddress

class DnsProxyListener: ProxyAdapter(){

    override fun onResolutionRequest(dnsRequest: DNSRequest): Boolean {
        Log.d("resolutionRequest", dnsRequest.host)
        Log.d("cycle", "resolutionRequest")

        val records = customElapsedTime("onResolutionRequest") {
            DnsResolver(dnsRequest.host)
        }


        var addr: InetAddress? = null
        records.forEach{
            Log.d("dnsResponse", it.hostAddress?.toString().toString())
            if(Patterns.IP_ADDRESS.matcher(it.hostAddress?.toString().toString()).matches()){
                if(addr == null){
                    addr = it
                }
            }
        }

        Log.d("currentAddr", addr?.hostAddress.toString())

        addr?.let { dnsRequest.response = InetSocketAddress(it, 443) }

        return true
    }
}