package com.feylabs.feature_example.di

import android.content.Context
import android.net.ConnectivityManager
import com.feylabs.feature_example.data.source.remote.service.LuminaApi
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
object LuminaModule {


    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideLuminaAPI(): LuminaApi {
        return Retrofit.Builder()
            .baseUrl("https://stockimg.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LuminaApi::class.java)
    }


}