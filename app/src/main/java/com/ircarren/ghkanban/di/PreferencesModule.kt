package com.ircarren.ghkanban.di

import android.content.Context
import com.ircarren.ghkanban.data.source.preferences.Preferences
import com.ircarren.ghkanban.data.source.preferences.PreferencesImpl
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
}