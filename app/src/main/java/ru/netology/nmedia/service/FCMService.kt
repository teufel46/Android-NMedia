package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import kotlin.random.Random


class FCMService : FirebaseMessagingService() {

    private val action = "action"
    private val content = "content"
    private val channelId = "remote"
    private val gson = Gson()

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {

        try {
            message.data[action]?.let {
                when (Action.valueOf(it)) {
                    Action.LIKE -> handleLike(
                        gson.fromJson(
                            message.data[content],
                            Like::class.java
                        )
                    )
                    Action.NEWPOST -> handleNewPost(
                        gson.fromJson(
                            message.data[content],
                            NewPost::class.java
                        )
                    )
                }
            }
        } catch (e: IllegalArgumentException) {
            handleUnknownMsg(message.data.toString())
        }
    }

    override fun onNewToken(token: String) {
        println(token)
    }

    private fun handleLike(content: Like) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_check_24)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    content.userName,
                    content.postAuthor,
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun handleNewPost(content: NewPost) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_living_24)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        getString(
                            R.string.notification_new_post,
                            content.userName,
                            content.content,
                        )
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun handleUnknownMsg(content: String?) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_bolt_24)
            .setContentTitle(getString(R.string.notification_unknown))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(content)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }
}

enum class Action {
    LIKE,
    NEWPOST
}

data class Like(
    val userId: Long,
    val userName: String,
    val postId: Long,
    val postAuthor: String,
)

data class NewPost(
    val userName: String,
    val content: String
)

