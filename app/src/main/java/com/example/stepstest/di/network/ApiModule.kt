package com.example.stepstest.di.network

import com.example.stepstest.data.StepsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): StepsApi {
        return retrofit.create(StepsApi::class.java)
    }
}