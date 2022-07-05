package com.anatame.flordia.data.network

import com.anatame.flordia.BuildConfig
import com.anatame.flordia.utils.Constants
import com.anatame.flordia.utils.customElapsedTime
import com.anatame.flordia.utils.retryIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_CBC_SHA
import okhttp3.CipherSuite.Companion.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256
import okhttp3.CipherSuite.Companion.TLS_RSA_WITH_AES_128_CBC_SHA
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.DnsOverHttps
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import kotlin.system.measureTimeMillis
import okhttp3.CipherSuite.Companion.TLS_RSA_WITH_3DES_EDE_CBC_SHA as TLS_RSA_WITH_3DES_EDE_CBC_SHA1


object AppNetworkClient {
    private var client: OkHttpClient? = null

    fun getClient(): OkHttpClient {
        return client ?: makeClient().also {
            client = it
        }
    }

    private fun makeClient(): OkHttpClient {
        val bootstrapClient = OkHttpClient.Builder()
            .build()

        val dns = DnsOverHttps.Builder().client(bootstrapClient)
            .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
            .build()

        val dispatcher = Dispatcher()
        dispatcher.maxRequestsPerHost = Int.MAX_VALUE
        val connectionPool = ConnectionPool(Int.MAX_VALUE, 1, TimeUnit.DAYS)

        return bootstrapClient.newBuilder()
            .apply{
                dns(dns)
                connectionSpecs(Collections.singletonList(ConnectionSpec.MODERN_TLS))
                connectionPool(connectionPool)
                dispatcher(dispatcher)
                eventListenerFactory(PrintEventListener.FACTORY)
                addInterceptor { chain ->
                    Timber.d("request Started")
                    customElapsedTime("BruhClient69") {
                        runBlocking {
                            retryIO(initialDelay = 0, times = Int.MAX_VALUE) {
                                chain.request().newBuilder()
                                    .header("User-Agent", Constants.userAgentList.first())
                                    .header("Connection", "keep-alive")
                                    .build()
                                    .let(chain::proceed)
                            }
                        }
                    }
                }
                pingInterval(1, TimeUnit.SECONDS)
            }.build()
    }

}