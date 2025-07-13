package com.crashtrace.mobile

import android.app.Application
import com.crashtrace.mobile.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CrashTraceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CrashTraceApplication)
            modules(appModule)
        }
    }
}
