package com.example.task2.di

import android.content.Context
import androidx.room.Room
import com.example.task2.data.api_service.ApiService
import com.example.task2.data.dao.UserDao
import com.example.task2.data.database.UserDataBase
import com.example.task2.data.repository.MainRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAPIUrl():String="https://jsonplaceholder.typicode.com"

    @Provides
    @Singleton
    fun provideMoshi():Moshi =
                  Moshi.Builder().run {
                      add(KotlinJsonAdapterFactory()).build()
                  }

    @Provides
    @Singleton
    fun providesOKHTTP(@ApplicationContext context: Context) : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level= HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun providesService(moshi: Moshi,httpClient: OkHttpClient,baseUrl:String) : ApiService  =
        Retrofit.Builder().run {
            baseUrl(baseUrl)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(httpClient)
            build()
        }.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideUserDataBase(@ApplicationContext context: Context):UserDataBase
          = Room.databaseBuilder(context,UserDataBase::class.java,"UserDb").allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideUserDao(userDataBase: UserDataBase):UserDao =userDataBase.userDao()

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService,userDao: UserDao):MainRepository= MainRepository(apiService,userDao)

}