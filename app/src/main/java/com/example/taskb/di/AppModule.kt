package com.example.taskb.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.example.taskb.R
import com.example.taskb.repository.local.GitHubDatabase
import com.example.taskb.repository.remote.api.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideGitHubApi(application: Application): GitHubApi {
        Log.d("TASKB===>", "Retrofit API initialized")
        return Retrofit.Builder()
            .baseUrl(application.getString(R.string.github_api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubDatabase(application: Application): GitHubDatabase {
        return Room.databaseBuilder(application, GitHubDatabase::class.java, "github_database")
            .build()
    }
}