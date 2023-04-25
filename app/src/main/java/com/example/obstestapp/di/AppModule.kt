package com.example.obstestapp.di

import android.content.Context
import com.example.obstestapp.data.IRemoteDataSource
import com.example.obstestapp.data.OBSService
import com.example.obstestapp.data.RemoteDataSource
import com.example.obstestapp.domain.IMainRepository
import com.example.obstestapp.domain.MainRepository
import com.example.obstestapp.utils.BASE_URL
import com.example.obstestapp.utils.IDispatcherProvider
import com.example.obstestapp.utils.MyDispatcherProvider
import com.example.obstestapp.utils.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun providesNetworkConnectionInterceptor(@ApplicationContext context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    @Singleton
    @Provides
    fun providesHttpClient(
        networkInterceptor: NetworkConnectionInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(networkInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): OBSService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        return retrofit.create(OBSService::class.java)
    }

    @Singleton
    @Provides
    fun providesRemoteDataSource(apiService: OBSService) =
        RemoteDataSource(apiService) as IRemoteDataSource

    @Singleton
    @Provides
    fun providesMainRepository(remoteDataSource: RemoteDataSource) =
        MainRepository(remoteDataSource) as IMainRepository

    @Singleton
    @Provides
    fun providesMyDispatcherProvider() = MyDispatcherProvider() as IDispatcherProvider

}
