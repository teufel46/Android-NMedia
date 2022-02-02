package ru.netology.nmedia.dto

data class Post(
    val id : Long = 0L,
    val author : String = "",
    val authorAvatar : String = "",
    val published : String = "",
    val content : String = "",
    var likedByMe : Boolean = false,
    var likedCount : Long = 0L,
    var sharedCount : Long = 0L,
    var viewedCount : Long = 0L
)
