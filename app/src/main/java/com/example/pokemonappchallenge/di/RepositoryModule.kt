package com.example.pokemonappchallenge.di

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.pokemonappchallenge.data.datasource.PokeWorkDataSourceImpl
import com.example.pokemonappchallenge.data.network.PokeApi
import com.example.pokemonappchallenge.data.repository.PokemonRepositoryImpl
import com.example.pokemonappchallenge.data.room.AppDataBase
import com.example.pokemonappchallenge.data.room.PokemonDataBase
import com.example.pokemonappchallenge.domain.datasource.PokeWorkDataSource
import com.example.pokemonappchallenge.domain.repository.PokemonRepository
import com.example.pokemonappchallenge.domain.worker.PokeWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Application): AppDataBase {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "PokemonDB"
        ).build()
    }

    @Singleton
    @Provides
    fun providePokemonRepository(db: AppDataBase, api: PokeApi): PokemonRepository
    = PokemonRepositoryImpl(api, db.pokemonDao())

    @Singleton
    @Provides
    fun providePokeWorkerDataSource(workManager: WorkManager): PokeWorkDataSource = PokeWorkDataSourceImpl(workManager)

    @Singleton
    @Provides
    fun provideWorkManager(context: Application): WorkManager = WorkManager.getInstance(context)

}