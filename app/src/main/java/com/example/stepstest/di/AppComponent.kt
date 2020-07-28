package com.example.stepstest.di

import android.app.Application
import com.example.stepstest.di.network.NetworkModule
import com.example.stepstest.StepsApp
import com.example.stepstest.di.viewModel.ViewModelModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBuilder::class,
        ViewModelModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: StepsApp)
}