package com.anatame.flordia.utils


import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.http.HttpObject
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpResponse
import org.littleshoot.proxy.HttpFilters
import org.littleshoot.proxy.HttpFiltersAdapter
import org.littleshoot.proxy.HttpFiltersSourceAdapter
import org.littleshoot.proxy.impl.DefaultHttpProxyServer
import timber.log.Timber


object ProxyServer {
    fun create(){
        try {
            val server = DefaultHttpProxyServer.bootstrap()
                .withPort(8080)
                .withFiltersSource(object : HttpFiltersSourceAdapter() {
                    override fun filterRequest(
                        originalRequest: HttpRequest?,
                        ctx: ChannelHandlerContext?
                    ): HttpFilters {
                        return object : HttpFiltersAdapter(originalRequest) {
                            override fun clientToProxyRequest(httpObject: HttpObject): HttpResponse? {
                                Timber.tag("clientToProxy").d(
                                    (httpObject as HttpRequest).uri
                                )
                                return null
                            }

                            override fun serverToProxyResponse(httpObject: HttpObject): HttpObject { // TODO: implement your filtering here
                                return httpObject
                            }
                        }
                    }
                })
                .start()

        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}