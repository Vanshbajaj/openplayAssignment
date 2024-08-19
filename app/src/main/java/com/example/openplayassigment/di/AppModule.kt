package com.example.openplayassigment.di

import android.content.Context
import com.example.openplayassigment.data.MovieApiService
import com.example.openplayassigment.data.MovieRepositoryImpl
import com.example.openplayassigment.data.local.AppDatabase
import com.example.openplayassigment.data.local.MovieDao
import com.example.openplayassigment.ui.movies.usecase.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGetPopularMoviesUseCase(
        movieRepositoryImpl: MovieRepositoryImpl
    ): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepositoryImpl)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: MovieApiService,
        movieDao: MovieDao
    ): MovieRepositoryImpl {
        return MovieRepositoryImpl(apiService, movieDao)
    }
}
