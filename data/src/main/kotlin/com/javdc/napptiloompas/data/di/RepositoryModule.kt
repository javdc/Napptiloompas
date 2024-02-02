package com.javdc.napptiloompas.data.di

import com.javdc.napptiloompas.data.remote.datasource.OompaLoompaRemoteDataSource
import com.javdc.napptiloompas.data.repository.OompaLoompaRepositoryImpl
import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideOompaLoompaRepository(
        oompaLoompaRemoteDataSource: OompaLoompaRemoteDataSource
    ): OompaLoompaRepository {
        return OompaLoompaRepositoryImpl(
            oompaLoompaRemoteDataSource,
        )
    }

}