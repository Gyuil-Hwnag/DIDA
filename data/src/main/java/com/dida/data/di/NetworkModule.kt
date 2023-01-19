package com.dida.data.di

import android.content.Context
import com.dida.data.BuildConfig
import com.dida.data.api.ApiClient.BASE_URL
import com.dida.data.api.MainAPIService
import com.dida.data.interceptor.BearerInterceptor
import com.dida.data.interceptor.ErrorResponseInterceptor
import com.dida.data.interceptor.XAccessTokenInterceptor
import com.dida.domain.usecase.main.RefreshTokenAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named("Main")
    fun provideOkHttpClient(): OkHttpClient = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
//            .addInterceptor(ErrorResponseInterceptor()) // Error Response
            .addInterceptor(BearerInterceptor()) // Refresh Token
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    } else {
        OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
//            .addInterceptor(ErrorResponseInterceptor()) // Error Response
            .addInterceptor(BearerInterceptor()) // Refresh Token
            .build()
    }

    @Singleton
    @Provides
    @Named("Main")
    fun provideRetrofit(@Named("Main") okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    @Named("Main")
    fun provideMainAPIService(@Named("Main") retrofit: Retrofit) : MainAPIService =
        retrofit.create(MainAPIService::class.java)
}
