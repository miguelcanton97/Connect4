package com.miguelcanton.conecta4.di

import android.content.Context
import android.content.SharedPreferences
import com.miguelcanton.conecta4.data.sharedpreferences.SharedPreferencesConstants.SHARED_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //di into Application level
class SharedPreferencesModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences =
        context.getSharedPreferences(SHARED_NAME, 0)
}