package com.example.stepstest.data.retrofit

import com.example.stepstest.data.interceptors.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT = 30
private const val READ_WRITE_TIMEOUT = 30

class Client() {

    fun okHttpClient(): OkHttpClient {
        val builder = defaultBuilder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(loggingInterceptor())
        return builder.build()
    }

    private fun defaultBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}