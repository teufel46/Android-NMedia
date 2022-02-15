package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemory

val empty = Post(
    id = 0L,
    author = "",
    authorAvatar = "",
    published = "",
    content = "",
    likedByMe = false,
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun likeById(id : Long){
        repository.likeById(id)
    }

    fun shareById(id : Long){
        repository.shareById(id)
    }

    fun removeById(id : Long){
        repository.removeById(id)
    }

    fun save(){
        edited.value?.let{// если есть пост, то мы его прочитаем и сохраним
            repository.save(it)
            edited.value = empty // редактируемый пост обнулим
        }
    }

    fun changeContent(content : String) {
        edited.value?.let {//прочитаем содержимое
            // проверим если контект совпадает с тем что уже есть
            if (it.content == content){
                return
            }

            edited.value = it.copy(content = content)
        }
    }



}