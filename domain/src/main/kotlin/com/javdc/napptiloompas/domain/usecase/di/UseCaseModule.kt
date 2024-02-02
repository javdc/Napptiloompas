package com.javdc.napptiloompas.domain.usecase.di

import com.javdc.napptiloompas.domain.repository.OompaLoompaRepository
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCase
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompaDetailsUseCaseImpl
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompasUseCase
import com.javdc.napptiloompas.domain.usecase.GetOompaLoompasUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetOompaLoompasUseCase(oompaLoompaRepository: OompaLoompaRepository): GetOompaLoompasUseCase =
        GetOompaLoompasUseCaseImpl(oompaLoompaRepository)

    @Provides
    fun provideGetOompaLoompaDetailsUseCase(oompaLoompaRepository: OompaLoompaRepository): GetOompaLoompaDetailsUseCase =
        GetOompaLoompaDetailsUseCaseImpl(oompaLoompaRepository)

}