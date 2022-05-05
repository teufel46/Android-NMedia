package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl (
    private val dao : PostDao
) : PostRepository{

    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun getAll_http(): List<Post> {
        TODO("Not yet implemented")
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likedCount = if (post.likedByMe) post.likedCount - 1 else post.likedCount + 1
                )
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun likeById_http(id: Long): Post {
        TODO("Not yet implemented")
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    sharedCount = post.sharedCount + 1
                )
            } else {
                post
            }
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        posts = posts.filter {
            it.id != id
        }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }
}