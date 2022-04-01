package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val likedCount: Long = 0,
    val sharedCount: Long = 0,
    val viewedCount: Long = 0,
    val videoURL: String = "",
) {
    fun toDto() = Post(id, author, authorAvatar, published, content, likedByMe,
        likedCount, sharedCount, viewedCount, videoURL )

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.authorAvatar, dto.published, dto.content, dto.likedByMe,
                dto.likedCount, dto.sharedCount, dto.viewedCount, dto.videoURL)
    }
}