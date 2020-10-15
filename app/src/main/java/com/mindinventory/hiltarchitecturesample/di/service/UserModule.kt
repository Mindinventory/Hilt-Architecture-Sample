package com.mindinventory.hiltarchitecturesample.di.service

import com.mindinventory.hiltarchitecturesample.data.repository.UserApi
import com.mindinventory.hiltarchitecturesample.data.repository.UserRepositoryIml
import com.mindinventory.hiltarchitecturesample.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UserModule {

    @Singleton
    @Provides
    fun provideRepository(api: UserApi) : UserRepository
    {
        return UserRepositoryIml(api)
    }

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit) : UserApi
    {
        return retrofit.create(UserApi::class.java)
    }

}