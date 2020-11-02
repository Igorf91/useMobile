package com.igorf.contacts

import android.app.Application
import com.igorf.contacts.di.networkModule
import com.igorf.contacts.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CustomApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}