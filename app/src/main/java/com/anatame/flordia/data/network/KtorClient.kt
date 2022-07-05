package com.anatame.flordia.data.network

import com.anatame.flordia.utils.customElapsedTime
import com.anatame.flordia.utils.retryIO
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import org.apache.http.conn.ConnectionKeepAliveStrategy
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor
import org.apache.http.impl.nio.reactor.IOReactorConfig
import timber.log.Timber
import java.io.IOException
import java.net.InetAddress

object KtorClient {
    fun makeRequest(url: String){

        val client = HttpClient(Apache) {
            engine {
                customizeClient {
                    setConnectionManager(PoolingNHttpClientConnectionManager(
                        DefaultConnectingIOReactor(IOReactorConfig.DEFAULT),
                        null
                    ) { resolveDns(it) })
                }
            }
        }

        GlobalScope.launch {
            withContext(Dispatchers.IO){
                try {
                    var time: Long = 0
                    customElapsedTime(timeTaken = { time = it}) {
                        runBlocking {
                            retryIO(initialDelay = 0) {
                                client.request(url)
                            }
                        }
                    }

                    Timber.tag("ktorMama").d("timeTaken: $time for $url")
                } catch(e: IOException){
                    e.printStackTrace()
                    Timber.tag("ktorMama").d("Error")
                }
            }
        }
    }

    private fun resolveDns(host: String): Array<InetAddress>{
        val arr = arrayOf<InetAddress>()

        return AppNetworkClient.getClient().dns.lookup(host).toTypedArray()
    }
}