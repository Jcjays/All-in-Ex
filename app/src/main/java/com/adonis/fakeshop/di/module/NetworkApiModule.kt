
package com.adonis.fakeshop.di.module

import com.adonis.fakeshop.remote.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class NetworkApiModule {
    @Provides
    internal fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}