package com.example.spacex.di

import android.content.Context
import androidx.room.Room
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.remote.SpaceXService
import com.example.spacex.repository.Repository
import com.example.spacex.util.Constants.APP_DATABASE
import com.example.spacex.util.Constants.SPACEX_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room
        .databaseBuilder(
            context.applicationContext,
            SpaceXDb::class.java,
            APP_DATABASE
        )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(SPACEX_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideSpaceXService(
        retrofit: Retrofit
    ): SpaceXService {
        return retrofit.create(SpaceXService::class.java)
    }

    @Singleton
    @Provides
    fun provideHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        okHttpLogger: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLogger)
            .build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    fun provideRepository(
        appDatabase: SpaceXDb,
        myAppService: SpaceXService
    ): Repository {
        return Repository(appDatabase, myAppService)
    }
}