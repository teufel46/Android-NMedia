package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()

    fun likeById(id : Long){
        repository.likeById(id)
    }

    fun shareById(id : Long){
        repository.shareById(id)
    }

    val data = repository.getAll()
}