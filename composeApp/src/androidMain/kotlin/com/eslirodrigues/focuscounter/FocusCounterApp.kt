package com.eslirodrigues.focuscounter

import android.app.Application
import com.eslirodrigues.focuscounter.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class FocusCounterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@FocusCounterApp)
        }
    }
}
