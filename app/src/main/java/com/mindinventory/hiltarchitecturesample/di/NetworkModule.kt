package com.mindinventory.hiltarchitecturesample.di

import com.mindinventory.hiltarchitecturesample.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    companion object {
        private const val HTTP_REQUEST_TIMEOUT = 2L
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit
    {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl("https://randomuser.me/")
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptors : ArrayList<Interceptor>) : OkHttpClient{
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
            .callTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)

        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideInterceptors() : ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        interceptors.add(loggingInterceptor)
        return interceptors
    }

}