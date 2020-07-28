package com.example.stepstest.di.network

import com.example.stepstest.data.StepsApi
import com.example.stepstest.data.useCase.CommentUseCase
import com.example.stepstest.data.useCase.impl.CommentNetworkUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommentUseCaseModule {

    @Singleton
    @Provides
    fun provideSignUpUseCase(api: StepsApi): CommentUseCase {
        return CommentNetworkUseCase(api)
    }
}