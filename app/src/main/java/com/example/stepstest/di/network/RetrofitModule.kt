package com.example.stepstest.di.network


import com.example.stepstest.BuildConfig
import com.example.stepstest.data.retrofit.Client
import com.example.stepstest.data.retrofit.RetrofitFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun gson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return RetrofitFactory.createApiRetrofit(
            gson,
            BuildConfig.API_URL,
            Client().okHttpClient()
        )
    }
}