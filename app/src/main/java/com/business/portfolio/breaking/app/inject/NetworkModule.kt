/*
 * Copyright 2022 Jesus
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.business.portfolio.breaking

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.business.portfolio.breaking.BuildConfig
import com.business.portfolio.breaking.data.remote.api.BreakingApi
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (!BuildConfig.DEBUG) return@apply
                addInterceptor(
                    HttpLoggingInterceptor { Timber.tag("OkHttp##\t").d(it) }
                        .apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }
            .cache(Cache(context.cacheDir, 5L * 1024 * 1024))
            .addInterceptor { forceCache(it) }
            .build()

    private fun forceCache(it: Interceptor.Chain, day: Int = 7): Response {
        val request = it.request().newBuilder().header(
            "Cache-Control",
            "max-stale=" + 60 * 60 * 24 * day
        ).build()
        val response = it.proceed(request)
        Timber.d("provideOkHttpClient: response: $response")
        Timber.i("provideOkHttpClient: cacheControl: ${response.cacheControl}")
        Timber.i("provideOkHttpClient: networkResponse: ${response.networkResponse}")
        return response
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideBaBrApi(retrofit: Retrofit.Builder): BreakingApi =
        retrofit.baseUrl(BreakingApi.BASE_URL).build().create(BreakingApi::class.java)
}
