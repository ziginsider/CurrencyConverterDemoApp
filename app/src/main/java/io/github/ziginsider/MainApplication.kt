package io.github.ziginsider

import android.app.Application
import android.content.Context
import io.github.ziginsider.di.AppComponent
import io.github.ziginsider.di.DaggerAppComponent

class MainApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApplication -> this.appComponent
        else -> this.applicationContext.appComponent
    }