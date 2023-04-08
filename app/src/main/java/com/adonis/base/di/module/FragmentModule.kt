package com.adonis.base.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(FragmentComponent::class)
@Module
object FragmentModule {

    //    @FragmentScoped
    //    @Provides
    //    fun provideMyService(): MyService {
    //        return MyService()
    //    }

    @FragmentScoped
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

}