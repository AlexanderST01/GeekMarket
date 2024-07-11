package com.ucne.geekmarket.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.geekmarket.data.local.database.GeekMarketDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
//    @Provides
//    @Singleton
//    fun providesMoshi(): Moshi =
//        Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//
//    @Provides
//    @Singleton
//    fun providesTicketApi(moshi: Moshi): TicketsApi {
//        return Retrofit.Builder()
////            .baseUrl("https://ap2ticket.azurewebsites.net/")
//            .baseUrl("https://localhost:7123/")
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//            .create(TicketsApi::class.java)
//    }

    @Provides
    @Singleton
    fun provideServicioDb(@ApplicationContext appContext: Context): GeekMarketDb {
        return Room.databaseBuilder(
            appContext,
            GeekMarketDb::class.java,
            "GeekMarketDb"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductoDao(database: GeekMarketDb) = database.productoDao()

}