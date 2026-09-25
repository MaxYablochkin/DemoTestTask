package com.dev.maxyablochkin.demotaskapp.core.network.di

import com.dev.maxyablochkin.demotaskapp.core.network.NewsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val coreNetworkModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestWithHeaders = original.newBuilder()
                    .header("User-Agent", "DemoTaskApp")
                    .build()
                chain.proceed(requestWithHeaders)
            }
            .addInterceptor(logging)
            .build()
    }

    single<NewsApiService> {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}

