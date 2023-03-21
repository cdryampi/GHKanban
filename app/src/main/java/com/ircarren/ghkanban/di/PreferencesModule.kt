package com.ircarren.ghkanban.di

import android.content.Context
import com.ircarren.ghkanban.data.source.preferences.Preferences
import com.ircarren.ghkanban.data.source.preferences.PreferencesImpl
import com.ircarren.ghkanban.data.source.preferences.PreferencesNetwork
import com.ircarren.ghkanban.data.source.preferences.PreferencesNetworkImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext app: Context): Preferences = PreferencesImpl(app)

    @Singleton
    @Provides
    fun providePreferencesNetwork(@ApplicationContext app: Context): PreferencesNetwork = PreferencesNetworkImpl(app)
}