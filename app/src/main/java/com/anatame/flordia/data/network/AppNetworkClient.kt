package com.anatame.flordia.data.network

import com.anatame.flordia.utils.retryIO
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.DnsOverHttps
import org.apache.http.params.HttpConnectionParams
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLHandshakeException


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

//        val dns = DnsOverHttps.Builder().client(bootstrapClient)
//            .url("https://dns.google/dns-query".toHttpUrl())
//            .build()


        return bootstrapClient.newBuilder()
            .dns(dns)
            .cache(null)

            .addInterceptor { chain ->
                runBlocking {
                    retryIO(times = 15, initialDelay = 50, factor = 1.0) {
                        chain.request().newBuilder()
                            .build()
                            .let(chain::proceed)
                    }
                }
            }
            .build()
    }
}