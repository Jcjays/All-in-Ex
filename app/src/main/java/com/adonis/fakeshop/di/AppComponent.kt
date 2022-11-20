package com.adonis.fakeshop.di


import com.adonis.fakeshop.di.module.FragmentModule
import android.app.Application
import com.adonis.fakeshop.MyApplication
import com.adonis.fakeshop.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        NetworkApiModule::class
    ]
)
interface AppComponent : AndroidInjector<MyApplication>{
    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}