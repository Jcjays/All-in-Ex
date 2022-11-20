package com.adonis.fakeshop.di.module

import androidx.lifecycle.ViewModelProvider
import com.adonis.fakeshop.di.ViewModelFactory
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}