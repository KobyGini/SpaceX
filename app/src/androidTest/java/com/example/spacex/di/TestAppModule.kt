package com.example.spacex.di

import android.content.Context
import androidx.room.Room
import com.example.spacex.data.local.SpaceXDb
import com.example.spacex.data.remote.SpaceXService
import com.example.spacex.repository.DataRepository
import com.example.spacex.repository.DataRepositoryImpl
import com.example.spacex.repository.FakeDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
class TestAppModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room
        .inMemoryDatabaseBuilder(context, SpaceXDb::class.java)
        .allowMainThreadQueries()
        .build()

    @Provides
    fun provideRepository(
        @Named("test_db")
        appDatabase:SpaceXDb
    ) : DataRepository = FakeDataRepositoryImpl(appDatabase)

}