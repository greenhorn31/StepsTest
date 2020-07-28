package com.example.stepstest

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.stepstest.di.AppComponent
import com.example.stepstest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class StepsApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        this.initDagger()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private fun initDagger() {
        this.appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        this.appComponent!!
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingActivityInjector
    }

}