package com.example.notification

import android.app.Notification
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import com.example.notification.databinding.ActivityMoreBinding


class MoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoreBinding


    private lateinit var bigImageNotification: Notification
    private val bigImageNotificationId = 0

    private lateinit var bigTextNotification: Notification
    private val bigTextNotificationId = 1

    private lateinit var inboxNotification: Notification
    private val inboxNotificationId = 2

    private lateinit var chatNotification: Notification
    private val chatNotificationId = 3

    private lateinit var multimediaNotification: Notification
    private val multimediaNotificationId = 4

    companion object {
        const val INTENT_REQUEST = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buildBigImageNotification()

        buildBigTextNotification()

        buildInboxNotification()

        buildChatNotification()

        buildMultimediaNotification()

        buttonsListener()
    }

    private fun buildMultimediaNotification() {
        val myBitmap = R.drawable.foto.createBitmap(this)

        val intent = Intent() //aca esta vacio pero deberia decir que hacer para pausar o comenzar la cancion
        val pendingIntent : PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(INTENT_REQUEST, PendingIntent.FLAG_IMMUTABLE)
        }

        multimediaNotification = NotificationCompat.Builder(this, MainActivity.MY_CHANNEL_ID).also {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.setContentTitle("Estilo de notificacion multimedia")
            it.setContentText("Nueva canción")
            it.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            it.addAction(R.drawable.ic_previous, "Previous", pendingIntent)
            it.addAction(R.drawable.ic_pause, "Pause", pendingIntent)
            it.addAction(R.drawable.ic_next, "Next", pendingIntent)
            it.setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView()
            )
            it.setLargeIcon(myBitmap)
        }.build()
    }

    private fun buildChatNotification() {
        val photoIcon = R.drawable.foto.createBitmap(this)
        val photo2Icon = R.drawable.foto2.createBitmap(this)

        val message1 = NotificationCompat.MessagingStyle.Message(
            "Hola Sharon! estoy aburrida",
            System.currentTimeMillis(),
            Person.Builder().also {
                it.setName("Sharon")
                it.setIcon(IconCompat.createWithAdaptiveBitmap(photoIcon))
            }.build()
        )

        val message2 = NotificationCompat.MessagingStyle.Message(
            "Hola Otra Sharon! yo tambien! me quiero ir!!",
            System.currentTimeMillis(),
            Person.Builder().also {
                it.setName("OtraSharon")
                it.setIcon(IconCompat.createWithAdaptiveBitmap(photo2Icon))
            }.build()
        )

        chatNotification = NotificationCompat.Builder(this, MainActivity.MY_CHANNEL_ID ).also {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.setStyle(
                NotificationCompat.MessagingStyle(
                    Person.Builder().also {
                        it.setName("Aylen")
                    }.build()
                )
                    .addMessage(message1)
                    .addMessage(message2)
            )
        }.build()
    }

    private fun buildInboxNotification() {
        inboxNotification = NotificationCompat.Builder(this, MainActivity.MY_CHANNEL_ID ).also {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.setContentTitle("usuario@gmail.com")
            it.setContentText("Usted tiene 3 nuevos mensajes.")
            it.setStyle(
                NotificationCompat.InboxStyle()
                    .addLine("Buen día, ha sido notificado para...")
                    .addLine("Hoy es el cumpleaños de...")
                    .addLine("Fulanito te invitó a que indiques...")
            )
        }.build()
    }

    private fun buildBigTextNotification() {
        val myBigmap : Bitmap = R.drawable.banner.createBitmap(this)

        bigTextNotification = NotificationCompat.Builder(this, MainActivity.MY_CHANNEL_ID).also {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.setContentTitle("Notificación con texto grande")
            it.setContentText("Contenido de la notificación")
            it.setStyle(
                NotificationCompat.BigTextStyle().bigText("HOLA Probando HOLA Probando HOLA Probando HOLA Probando HOLA Probando HOLA Probando HOLA Probando HOLA Probando HOLA Probando HOLA Probando ")
            )
            it.setLargeIcon(myBigmap)
        }.build()
    }

    private fun buttonsListener() {
        val notificationManager = NotificationManagerCompat.from(this)

        binding.btnBigImageNotification.setOnClickListener {
            notificationManager.notify(bigImageNotificationId, bigImageNotification)
        }

        binding.btnBigTextNotification.setOnClickListener {
            notificationManager.notify(bigTextNotificationId, bigTextNotification)
        }

        binding.btnInboxNotification.setOnClickListener {
            notificationManager.notify(inboxNotificationId, inboxNotification)
        }

        binding.btnChatNotification.setOnClickListener {
            notificationManager.notify(chatNotificationId, chatNotification)
        }

        binding.btnMultimediaNotification.setOnClickListener {
            notificationManager.notify(multimediaNotificationId, multimediaNotification)
        }
    }

    private fun buildBigImageNotification() {
        val myBigmap : Bitmap = R.drawable.banner.createBitmap(this)

        bigImageNotification = NotificationCompat.Builder(this, MainActivity.MY_CHANNEL_ID).also {
            it.setSmallIcon(R.drawable.ic_launcher_foreground)
            it.setContentTitle("Notificación con imagen grande")
            it.setContentText("Contenido de la notificación")
            it.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(myBigmap)
                    .bigLargeIcon(null) //que se muestre el icono en la parte derecha en miniatura
            )
            it.setLargeIcon(myBigmap)
        }.build()
    }

}