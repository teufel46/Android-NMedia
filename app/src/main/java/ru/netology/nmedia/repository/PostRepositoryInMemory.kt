package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemory : PostRepository {

    private val post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        published = "21 мая в 18:36",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        likedCount = 999,
        sharedCount = 9998,
        viewedCount = 2678
    )

    private val _data = MutableLiveData(post)

    override fun like() {
        val post = _data.value ?: return
        _data.value = _data.value?.copy(
            likedCount = if (post.likedByMe) post.likedCount - 1 else post.likedCount + 1,
            likedByMe = !post.likedByMe
        )
    }

    override fun share() {
        val post = _data.value ?: return
        _data.value = _data.value?.copy(
            sharedCount = post.sharedCount + 1
        )
    }

    override val data: LiveData<Post>
        get() = _data
}