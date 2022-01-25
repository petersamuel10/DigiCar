package com.peter.digicaradmin.di

import android.content.Context
import com.peter.digicaradmin.data.api.ApiHelperImpl
import com.peter.digicaradmin.data.api.ApiService
import com.peter.digicaradmin.base.App
import com.peter.digicaradmin.data.api.ApiHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext context: Context): App {
        return context as App
    }

    @Provides
    fun provideBaseUrl() = "https://api.openweathermap.org/data/2.5/"


    @Provides
    @Singleton
    fun provideRetrofit(baseURL: String) =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

}