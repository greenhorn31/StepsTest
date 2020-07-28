package com.example.stepstest.di

import com.example.stepstest.ui.scenes.comments.CommentsFragment
import com.example.stepstest.ui.scenes.rangePick.RangePickFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector()
    abstract fun provideRangePickFragment(): RangePickFragment

    @ContributesAndroidInjector()
    abstract fun provideCommentsFragment(): CommentsFragment
}