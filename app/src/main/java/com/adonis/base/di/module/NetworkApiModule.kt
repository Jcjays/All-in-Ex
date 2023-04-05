
package com.adonis.base.di.module

import com.adonis.base.arch.data.ApiService
import com.adonis.base.arch.data.MainRepository
import com.adonis.base.arch.data.RepositoryService
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
    internal fun provideRepositoryService(apiService: ApiService): RepositoryService {
        return MainRepository(apiService)
    }

}