package com.example.stepstest.di

import com.example.stepstest.ui.scenes.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMain(): MainActivity
}