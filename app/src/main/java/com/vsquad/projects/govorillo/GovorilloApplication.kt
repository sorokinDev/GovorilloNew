package com.vsquad.projects.govorillo

import android.app.Application
import com.vsquad.projects.govorillo.dagger.AppComponent
import com.vsquad.projects.govorillo.dagger.DaggerAppComponent


/**
 * Created by Vova on 13.11.2017.
 */
class GovorilloApplication : Application() {

    companion object {
        lateinit var INSTANCE: GovorilloApplication
    }

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder().build()
        }
        return appComponent!!
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}