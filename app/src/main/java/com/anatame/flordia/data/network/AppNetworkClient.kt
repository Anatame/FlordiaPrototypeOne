package com.anatame.flordia.data.network

import android.net.SocketKeepalive
import com.anatame.flordia.utils.retryIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.DnsOverHttps
import org.apache.http.params.HttpConnectionParams
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLHandshakeException
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime


object AppNetworkClient {
    private var client: OkHttpClient? = null
//    private var mockClient: OkHttpClient? = null
    
    suspend fun warmUpReq(url: String){
        withContext(Dispatchers.IO){
            try {
                val warmUpTime = measureTimeMillis{
                    val req = Request.Builder()
                        .url(url)
                        .head()
                        .build()

                    getClient().newCall(req).execute()
                }

                Timber.tag("warmupTimeTaken").d(warmUpTime.toString())
            } catch(e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun getClient(): OkHttpClient {
        return client ?: makeClient().also {
            client = it
        }
    }

    private fun makeClient(): OkHttpClient {

        val appCache = Cache(File("cacheDir", "okhttpcache"), 10 * 1024 * 1024)

        val bootstrapClient = OkHttpClient.Builder()
            .cache(appCache)
            .build()

        val dns = DnsOverHttps.Builder().client(bootstrapClient)
            .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
            .build()

//        val dns = DnsOverHttps.Builder().client(bootstrapClient)
//            .url("https://dns.google/dns-query".toHttpUrl())
//            .build()


        return bootstrapClient.newBuilder()
            .dns(dns)
            .cache(appCache)
            .retryOnConnectionFailure(true)
            .eventListenerFactory(PrintEventListener.FACTORY)
            .addInterceptor { chain ->
                Timber.d("request Started")
                runBlocking {
                    retryIO(initialDelay = 0) {
                        chain.request().newBuilder()
                            .build()
                            .let(chain::proceed)
                    }
                }
            }
            .pingInterval(1, TimeUnit.SECONDS)
            .build()
    }
}