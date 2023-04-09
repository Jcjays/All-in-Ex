
package com.adonis.base.di.module

import com.adonis.base.arch.data.*
import com.adonis.base.arch.data.firebase.AuthRepository
import com.adonis.base.arch.data.firebase.AuthRepositoryService
import com.adonis.base.arch.data.retrofit.ApiService
import com.adonis.base.arch.data.retrofit.MainRepository
import com.adonis.base.arch.data.retrofit.MainRepositoryService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object NetworkApiModule {

    @Provides
    internal fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    internal fun provideRepositoryService(apiService: ApiService): MainRepositoryService {
        return MainRepository(apiService)
    }

    @Provides
    internal fun provideAuthRepositoryService(auth : FirebaseAuth, db : FirebaseFirestore) : AuthRepositoryService {
        return AuthRepository(auth, db)
    }

}