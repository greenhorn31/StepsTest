package com.example.stepstest.data.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    fun createApiRetrofit(
        gson: Gson,
        baseEndpoint: String,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val converterFactory = converterFactory(gson)
        return Retrofit.Builder()
            .baseUrl(baseEndpoint)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun converterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }
}