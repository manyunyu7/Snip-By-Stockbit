package com.feylabs.snips.di

import android.content.Context
import android.net.ConnectivityManager
import com.feylabs.movie_genre.data.source.remote.service.MovieGenreApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    @ConnectivityManagerSnips
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    @RetrofitSnips
    fun provideLuminaAPI(): MovieGenreApi {
        val apiKey = "2671c64cf6ce8c1265cf8ce1c3543bf9" // Replace this with your actual TMDb API key

        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(apiKey)) // Add OkHttpClient with the API key
            .build()
            .create(MovieGenreApi::class.java)
    }

    private fun provideOkHttpClient(apiKey: String): OkHttpClient {
        val interceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            // Append API key as a query parameter
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val request = originalRequest.newBuilder()
                .url(url)
                .build()

            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RetrofitSnips

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ConnectivityManagerSnips




}