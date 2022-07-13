package com.anatame.flordia.utils.server.plugins.dns_resolver

import com.anatame.flordia.BuildConfig
import com.anatame.flordia.utils.server.PluginInterface
import com.anatame.flordia.utils.server.plugins.dns_resolver.DnsProxyListener
import io.github.krlvm.powertunnel.BuildConstants
import io.github.krlvm.powertunnel.sdk.plugin.PluginInfo
import io.github.krlvm.powertunnel.sdk.proxy.ProxyListener

object DnsResolverPlugin: PluginInterface {
    override fun getPluginInfo(): PluginInfo {
        return PluginInfo(
            "dns_resolver",
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE,
            "Dns Resolver Android",
            "Uses DOH to resolve dns queries",
            "",
            "",
            null,
            BuildConstants.VERSION_CODE,
            null
        )
    }
    override fun getListener(): ProxyListener{
        return DnsProxyListener()
    }
}