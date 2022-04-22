package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.AppDbRoom
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.*

val empty = Post(
    id = 0L,
    author = "",
    authorAvatar = "",
    published = "",
    content = "",
    likedByMe = false,
    videoURL = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
 //   private val repository : PostRepository = PostRepositorySQLiteImpl(
//        AppDb.getInstance(application).postDao
  //  )
    private val repository : PostRepository = PostRepositoryRoomImpl(
        AppDbRoom.getInstance(application).postDaoRoom()
    )

    val data = repository.getAll()

    val edited = MutableLiveData(empty)

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun shareById(id: Long) {
        repository.shareById(id)
    }

    fun removeById(id: Long) {
        repository.removeById(id)
    }

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = content)
    }
}