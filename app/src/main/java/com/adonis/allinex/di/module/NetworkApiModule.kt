package com.adonis.allinex.di.module

import com.adonis.allinex.arch.data.*
import com.adonis.allinex.arch.data.firebase.FirebaseRepository
import com.adonis.allinex.arch.data.firebase.FirebaseRepositoryService
import com.adonis.allinex.arch.data.retrofit.ApiService
import com.adonis.allinex.arch.data.retrofit.MainRepository
import com.adonis.allinex.arch.data.retrofit.MainRepositoryService
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.identity.SignInClient
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
    internal fun provideAuthRepositoryService(
        auth: FirebaseAuth,
        db: FirebaseFirestore,
        googleOneTapClient: SignInClient,
        facebookLoginManager: LoginManager,
    ): FirebaseRepositoryService {
        return FirebaseRepository(auth, db, googleOneTapClient, facebookLoginManager)
    }

}