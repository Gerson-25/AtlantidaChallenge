package com.example.pokemonappchallenge.data.datasource

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.domain.datasource.PokeWorkDataSource
import com.example.pokemonappchallenge.domain.usecases.GetPokemonsUseCase
import com.example.pokemonappchallenge.domain.worker.PokeWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class PokeWorkDataSourceImpl(
    private val worker: WorkManager
): PokeWorkDataSource {
    override suspend fun getPokemonList() {
        val createRequest = PeriodicWorkRequestBuilder<PokeWorker>(16L, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            ).addTag("POKE_WORKER_REQUEST").build()

        worker.enqueueUniquePeriodicWork(
            "POKE_WORKER_TASK",
            ExistingPeriodicWorkPolicy.KEEP,
            createRequest
        )

    }
}