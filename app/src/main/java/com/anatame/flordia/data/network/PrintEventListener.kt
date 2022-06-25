package com.anatame.flordia.data.network

import okhttp3.*
import timber.log.Timber
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.atomic.AtomicLong

class PrintEventListener(
    private val callId: Long,
    private val callStartMillis: Long
): EventListener() {

    companion object FACTORY: Factory {
        val nextCallId = AtomicLong(1L)
        override fun create(call: Call): EventListener {
            val callId = nextCallId.getAndIncrement()
            Timber.tag("printEventCreate").d("%04d %s%n", callId, call.request().url)
            return PrintEventListener(callId, System.currentTimeMillis())
        }
    }


    private fun printEvent(name: String) {
        val elapsedMillis = System.currentTimeMillis() - callStartMillis
        Timber.tag("printEvent").d("$callId, ${elapsedMillis}, $name")
    }

    override fun callStart(call: Call) {
        printEvent("callStart")
    }

    override fun callFailed(call: Call, ioe: IOException) {
        printEvent("callFailed")
    }

    override fun callEnd(call: Call) {
        printEvent("callEnd")
    }

    override fun dnsStart(call: Call, domainName: String) {
        printEvent("dnsStart")
    }

    override fun dnsEnd(call: Call, domainName: String, inetAddressList: List<InetAddress>) {
        printEvent("dnsEnd")
    }

    override fun connectStart(call: Call, inetSocketAddress: InetSocketAddress, proxy: Proxy) {
        printEvent("connectStart")
    }

    override fun connectFailed(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?,
        ioe: IOException
    ) {
        printEvent("connect failed")
    }

    override fun connectionAcquired(call: Call, connection: Connection) {
        printEvent("connectionAcquired")
    }

    override fun connectionReleased(call: Call, connection: Connection) {
        printEvent("connectionReleased")
    }

    override fun connectEnd(
        call: Call,
        inetSocketAddress: InetSocketAddress,
        proxy: Proxy,
        protocol: Protocol?
    ) {
        printEvent("connectEnd")
    }

    override fun responseBodyStart(call: Call) {
        printEvent("responseBodyStart")
    }

    override fun responseBodyEnd(call: Call, byteCount: Long) {
        printEvent("responseBodyEnd")
    }

    override fun cacheConditionalHit(call: Call, cachedResponse: Response) {
        printEvent("cacheConditionalHit")
    }

    override fun cacheHit(call: Call, response: Response) {
        printEvent("cacheHit")
    }

    override fun cacheMiss(call: Call) {
        printEvent("cacheMiss")
    }

    override fun canceled(call: Call) {
        printEvent("canceled")
    }

    override fun proxySelectEnd(call: Call, url: HttpUrl, proxies: List<Proxy>) {
        printEvent("proxySelectEnd")
    }

    override fun proxySelectStart(call: Call, url: HttpUrl) {
        printEvent("proxySelectStart")
    }

    override fun requestBodyEnd(call: Call, byteCount: Long) {
        printEvent("requestBodyEnd")
    }

    override fun requestBodyStart(call: Call) {
        printEvent("requestBodyStart")
    }

    override fun requestFailed(call: Call, ioe: IOException) {
        printEvent("requestFailed")
    }

    override fun requestHeadersEnd(call: Call, request: Request) {
        printEvent("requestHeadersEnd")
    }

    override fun requestHeadersStart(call: Call) {
        printEvent("requestHeadersStart")
    }

    override fun responseFailed(call: Call, ioe: IOException) {
        printEvent("responseFailed")
    }

    override fun responseHeadersEnd(call: Call, response: Response) {
        printEvent("responseHeadersEnd")
    }

    override fun responseHeadersStart(call: Call) {
        printEvent("responseHeadersStart")
    }

    override fun satisfactionFailure(call: Call, response: Response) {
        printEvent("satisfactionFailure")
    }

    override fun secureConnectEnd(call: Call, handshake: Handshake?) {
        printEvent("secureConnectEnd")
    }

    override fun secureConnectStart(call: Call) {
        printEvent("secureConnectStart")
    }
}