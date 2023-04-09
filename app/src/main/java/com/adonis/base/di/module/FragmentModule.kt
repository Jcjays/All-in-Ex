package com.adonis.base.di.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@InstallIn(FragmentComponent::class)
@Module
object FragmentModule {

    //    @FragmentScoped
    //    @Provides
    //    fun provideMyService(): MyService {
    //        return MyService()
    //    }


}