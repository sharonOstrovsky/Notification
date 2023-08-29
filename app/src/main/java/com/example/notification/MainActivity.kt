package com.example.notification

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notification.AlarmNotification.Companion.NOTIFICATION_ID
import com.example.notification.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private lateinit var custumNotification: Notification
    private val customNotificationID = 5

    companion object{
        const val MY_CHANNEL_ID = "myChannel"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        createChannel()

        buttonListener()


    }

    private fun buttonListener(){



        binding.btnSimpleNotification .setOnClickListener {
            createSimpleNotification()
        }
        binding.btnAlarmNotification .setOnClickListener {
            scheduleNotification()
        }


        binding.btnCustomNotification.setOnClickListener {
            buildCustumNotification()
        }

        binding.btnMoreNotifications.setOnClickListener {
            redirectToMoreNotifications()
        }
    }

    fun redirectToMoreNotifications(){
        val intent = Intent(this, MoreActivity::class.java)
        startActivity(intent)
    }

    private fun buildCustumNotification() {
        val notificationLayoutSmall = RemoteViews(packageName,R.layout.notification_small)
        val notificationLayoutExpanded = RemoteViews(packageName,R.layout.notification_expanded)


        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)
/*
        custumNotification = NotificationCompat.Builder(this, MY_CHANNEL_ID).also {
            it.setSmallIcon(R.drawable.notification)
            it.setStyle(NotificationCompat.DecoratedCustomViewStyle())
            it.setCustomContentView(notificationLayoutSmall)
            it.setCustomBigContentView(notificationLayoutExpanded)
        }.build()
 */

        var custumNotification = NotificationCompat.Builder(this, MY_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayoutSmall)
            .setCustomBigContentView(notificationLayoutExpanded)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(defaultSoundUri)

        with(NotificationManagerCompat.from(this)){
            notify(1,custumNotification.build())
        }

    }

    private fun scheduleNotification() {
        val intent = Intent(applicationContext, AlarmNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, Calendar.getInstance().timeInMillis + 15000, pendingIntent) //en 15 segundos sale la notificacion
    }


    fun createSimpleNotification(){

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)


        var builder = NotificationCompat.Builder(this, MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContentTitle("Simple Notification")
            .setContentText("Hola")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Esta es una notificacion que se envia en el momento.")
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(defaultSoundUri)

        with(NotificationManagerCompat.from(this)){
            notify(1,builder.build())
        }
    }

    private fun createChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "SUSCRIBITE"
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}