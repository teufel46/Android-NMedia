package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private var post = listOf(
        Post(
            id = 2,
            author = "Нетология. Пост 2",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedCount = 1999,
            sharedCount = 0,
            viewedCount = 2678
        ),
        Post(
            id = 1,
            author = "Нетология. Пост 1",
            published = "22 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likedCount = 999,
            sharedCount = 9998,
            viewedCount = 13457
        )
    )

    private val data = MutableLiveData(post)

    override fun likeById(id: Long) {
        post = post.map { post ->
             if (post.id == id) {
                 post.copy(
                     likedByMe = !post.likedByMe,
                     likedCount = if (post.likedByMe) post.likedCount - 1 else post.likedCount + 1)
             } else {
                 post
             }
        }
        data.value = post
    }

    override fun shareById(id: Long) {
        post = post.map { post ->
            if (post.id == id) {
                post.copy(
                    sharedCount = post.sharedCount + 1)
            } else {
                post
            }
        }
        data.value = post
    }

    override fun getAll(): LiveData<List<Post>> = data
}