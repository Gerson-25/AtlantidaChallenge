package com.example.pokemonappchallenge.domain.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.pokemonappchallenge.R
import com.example.pokemonappchallenge.data.utils.Constants.CHANNEL_ID
import com.example.pokemonappchallenge.data.utils.Constants.NOTIFICATION_ID
import com.example.pokemonappchallenge.data.utils.Constants.NOTIFICATION_TITLE
import com.example.pokemonappchallenge.domain.usecases.GetPokemonListPerodicallyUseCase
import com.example.pokemonappchallenge.ui.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collectLatest

@HiltWorker
class PokeWorker @AssistedInject constructor(
    private val getPokemonsUseCase: GetPokemonListPerodicallyUseCase,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
): CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            getPokemonsUseCase.invoke().collectLatest {
                when (it){
                    is com.example.pokemonappchallenge.domain.response.Result.Success<*> -> {
                        makeStatusNotification(this.applicationContext.getString(R.string.updated_list), context = this.applicationContext)
                    }
                    is com.example.pokemonappchallenge.domain.response.Result.Error -> {
                        makeStatusNotification(this.applicationContext.getString(R.string.updating_error), context = this.applicationContext)
                        Result.failure()
                    }
                    else -> {
                        makeStatusNotification(this.applicationContext.getString(R.string.updating), context = this.applicationContext)
                    }
                }
            }

            Result.success()


        } catch (e: Throwable) {
            Result.failure()
        }
    }

    private fun makeStatusNotification(
        message: String,
        context: Context) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(UPDATE_ARG, true)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.poke_logo)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)


        // Show the notification
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        }

    }

    companion object {
        const val UPDATE_ARG = "update"
    }


}