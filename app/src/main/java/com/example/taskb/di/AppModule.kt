package com.example.taskb.di

import android.app.Application
import android.content.Context
import com.example.taskb.R
import com.example.taskb.api.GitHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        return Retrofit.Builder()
            .baseUrl(application.getString(R.string.github_api_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }
}