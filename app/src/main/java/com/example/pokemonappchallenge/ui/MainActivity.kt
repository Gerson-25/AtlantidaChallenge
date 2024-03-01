package com.example.pokemonappchallenge.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import com.example.pokemonappchallenge.R
import com.example.pokemonappchallenge.data.models.Pokemon
import com.example.pokemonappchallenge.data.utils.Constants
import com.example.pokemonappchallenge.databinding.ActivityMainBinding
import com.example.pokemonappchallenge.domain.worker.PokeWorker
import com.example.pokemonappchallenge.domain.worker.PokeWorker.Companion.UPDATE_ARG
import com.example.pokemonappchallenge.ui.view.recyclerview.PokeRecyclerView
import com.example.pokemonappchallenge.ui.viewmodel.PokeViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PokeRecyclerView.OnEmptyListener {

    private lateinit var binding: ActivityMainBinding
    private val viewmodel: PokeViewModel by viewModels()
    private lateinit var pokemonRecyclerView: PokeRecyclerView
    private var update = false

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it){
            Log.d("PERMISSIONS_REQUEST", "Permissions were granted")
        } else {
            Log.d("PERMISSIONS_REQUEST", "Permissions were not granted")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        if (extras != null) {
            update = extras.getBoolean(UPDATE_ARG)
        }

        if (update){
            viewmodel.getPokemonList()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmodel.getPokemonList()
        viewmodel.getGenerations()

        requestNotificationPermissions()

        viewmodel.isLoading.observe(this){
            showLoading(it)
        }

        viewmodel.pokemonList.observe(this){
            val sortedList = it.sortedBy { it.id }
            setRecyclerView(sortedList.toMutableList())
            launchFetchInBackground()
            showEmptyLayout(false)
        }

        viewmodel.generations.observe(this){generations ->
            for (generation in generations){
                val chip = Chip(this)
                chip.text = generation.name
                chip.isCheckable = true
                chip.setOnCheckedChangeListener { compoundButton, b ->
                    if (b)
                        pokemonRecyclerView.applyFilter(generation)
                    else
                        pokemonRecyclerView.deleteFilter(generation)
                }
                binding.chipGeneration.addView(chip)
            }
        }

        binding.filterButton.setOnClickListener {
            showFilters()
        }

    }

    private fun showFilters(){
        binding.chipGeneration.visibility =
            if (binding.chipGeneration.visibility == View.GONE)
                View.VISIBLE
            else
                View.GONE
    }

    private fun setRecyclerView(pokemonList: MutableList<Pokemon>){
        pokemonRecyclerView = PokeRecyclerView(this ,pokemonList, listener = this)
        binding.pokemonRV.adapter = pokemonRecyclerView
        binding.pokemonRV.layoutManager = GridLayoutManager(this, 2)
    }

    private fun showLoading(show: Boolean){
        binding.showProgress.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun launchFetchInBackground(){
        val createRequest = PeriodicWorkRequestBuilder<PokeWorker>(16L, TimeUnit.SECONDS)
                .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).addTag("POKE_WORKER_REQUEST").build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "POKE_WORKER_TASK",
            ExistingPeriodicWorkPolicy.UPDATE,
            createRequest
        )
    }

    private fun requestNotificationPermissions(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("PERMISSIONS_REQUEST", "Asking for permissions")
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            Log.d("PERMISSIONS_REQUEST", "Permissions are already granted")
        }
    }

    override fun showEmptyLayout(show: Boolean) {
        binding.emptyState.visibility = if (show) View.VISIBLE else View.GONE
    }
}
