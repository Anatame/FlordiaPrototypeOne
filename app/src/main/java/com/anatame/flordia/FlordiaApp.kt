package com.anatame.flordia

import android.app.Application
import com.google.android.gms.security.ProviderInstaller
import timber.log.Timber
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext

class FlordiaApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initTimber()

        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(applicationContext);
            val sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return super.createStackElementTag(element) + ":${element.methodName}:${element.lineNumber}"
                }
            })
        }
    }
}