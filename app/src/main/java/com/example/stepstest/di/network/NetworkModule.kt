package com.example.stepstest.di.network

import dagger.Module

@Module(
    includes = [
        RetrofitModule::class,
        ApiModule::class,
        CommentUseCaseModule::class
    ]
)
abstract class NetworkModule