package com.anatame.flordia.data.network

import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.dnsoverhttps.DnsOverHttps


object AppNetworkClient {
    private var client: OkHttpClient? = null

    fun getClient(): OkHttpClient {
        return client ?: makeClient().also {
            client = it
        }
    }

    private fun makeClient(): OkHttpClient {
        val bootstrapClient = OkHttpClient.Builder()
            .cache(null)
            .build()

        val dns = DnsOverHttps.Builder().client(bootstrapClient)
            .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
            .build()

        return bootstrapClient.newBuilder()
            .dns(dns)
            .build()
    }
}