package com.example.mystore.domain

import android.content.Context
import androidx.room.Room
import com.example.mystore.data.network.ApiService
import com.example.mystore.data.network.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    internal fun provideMoshiInit(): Moshi {
        val moshi  = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return moshi
    }

    @Provides
    @Singleton
    internal fun providesRetrofitInit(): ApiService {

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(provideMoshiInit()))
            .baseUrl(BASE_URL)
            .build()

        return retrofit.create(ApiService::class.java)
    }



}