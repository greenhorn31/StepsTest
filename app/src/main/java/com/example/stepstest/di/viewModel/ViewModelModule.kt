package com.example.stepstest.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stepstest.ui.scenes.comments.CommentsViewModel
import com.example.stepstest.ui.scenes.rangePick.RangePickViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RangePickViewModel::class)
    internal abstract fun bindRangePickViewModel(viewModel: RangePickViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    internal abstract fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel
}