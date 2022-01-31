package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

fun convertCount2String(count : Long) : String{
    val result = when {
        count < 1_000 ->  count.toString()
        count in 1_000..1_099 -> "1K"
        count in 1_100..9_999 -> String.format("%.1fK", (count / 100).toDouble() / 10)
        count in 10_000..999_999 -> (count / 1000).toString() + "K"
        count in 1_000_000..1_999_999 -> "1M"
        else -> String.format("%.1fМ", (count / 100_000).toDouble() / 10)
    }
    return result
 }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedCount = 999,
            sharedCount = 9998,
            viewedCount = 2678
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likedCount.text = convertCount2String(post.likedCount)
            sharedCount.text = convertCount2String(post.sharedCount)
            viewedCount.text = convertCount2String(post.viewedCount)
            liked.setOnClickListener {
                println("Like")
                if (!post.likedByMe) {
                    liked.setImageResource(R.drawable.ic_baseline_favorite_24)
                    post.likedCount++
                } else {
                    liked.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    post.likedCount--
                }
                post.likedByMe = !post.likedByMe
                likedCount.text = convertCount2String(post.likedCount)
            }
            shared.setOnClickListener {
                post.sharedCount++
                sharedCount.text = convertCount2String(post.sharedCount)
            }
        }
        /*
            val author = findViewById<TextView>(R.id.author)
            author.text = post.author

            val published = findViewById<TextView>(R.id.published)
            published.text = post.published

            val content = findViewById<TextView>(R.id.content)
            content.text = post.content

            val likedCount = findViewById<TextView>(R.id.likedCount)
            likedCount.text = post.likedCount.toString()

            val like = findViewById<ImageButton>(R.id.liked)
            like.setOnClickListener {
                println("Like")
                if (!post.likedByMe) {
                    liked.setImageResource(R.drawable.ic_baseline_favorite_24)
                    post.likedCount++

                } else {
                    liked.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    post.likedCount--
                }
                post.likedByMe = !post.likedByMe
                likedCount.text = post.likedCount.toString()
            }
        }

         */


    }
}