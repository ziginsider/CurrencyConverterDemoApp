package io.github.ziginsider.di

import androidx.viewbinding.BuildConfig
import dagger.Module
import dagger.Provides
import io.github.ziginsider.data.api.ApiService
import io.github.ziginsider.base.AppConstants
import io.github.ziginsider.base.AppConstants.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger())
            .connectTimeout(AppConstants.TIME_OUT_CONNECT_SEC, TimeUnit.SECONDS)
            .readTimeout(AppConstants.TIME_OUT_READ_SEC, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun logger(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS).level =
                HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }
}