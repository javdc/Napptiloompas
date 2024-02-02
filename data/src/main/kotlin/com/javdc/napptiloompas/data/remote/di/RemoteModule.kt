package com.javdc.napptiloompas.data.remote.di

import com.javdc.napptiloompas.data.BuildConfig
import com.javdc.napptiloompas.data.remote.api.OompaLoompaService
import com.javdc.napptiloompas.data.remote.datasource.OompaLoompaRemoteDataSource
import com.javdc.napptiloompas.data.remote.datasource.OompaLoompaRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    private const val API_BASE_URL = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/"

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingLevel = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
        return HttpLoggingInterceptor().setLevel(loggingLevel)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
            .also {
                it.dispatcher.maxRequests = 8
                it.dispatcher.maxRequestsPerHost = 4
            }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOompaLoompaService(retrofit: Retrofit): OompaLoompaService =
        retrofit.create(OompaLoompaService::class.java)

    @Provides
    @Singleton
    fun provideOompaLoompaRemoteDataSource(oompaLoompaService: OompaLoompaService): OompaLoompaRemoteDataSource =
        OompaLoompaRemoteDataSourceImpl(oompaLoompaService)

}