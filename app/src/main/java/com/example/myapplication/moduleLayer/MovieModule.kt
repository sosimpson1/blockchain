package com.example.myapplication.moduleLayer

import android.content.Context
import androidx.room.Room
import com.example.myapplication.api.MoviesApi
import com.example.myapplication.databases.CharacterDaos
import com.example.myapplication.databases.MovieDatabase
import com.example.myapplication.utilis.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(Constant.AppModuleKey.API_KEY, Constant.PUBLIC_KEY)
                .addQueryParameter(Constant.AppModuleKey.TIMESTAMP, Constant.timeStamp)
                .addQueryParameter(Constant.AppModuleKey.HASH_KEY, Constant.hash())
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    @Provides
    @Singleton
    fun provideMarvelApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideMarvelDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, Constant.DbConstant.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCharacterDao(marvelDatabase: MovieDatabase): CharacterDaos =
        marvelDatabase.getCharacterDao()

}
