package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDaoRoom
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val daoRoom: PostDaoRoom,
) : PostRepository {
    override fun getAll() = Transformations.map(daoRoom.getAll()) { list ->
        list.map {
            it.toDto()
        }
    }

    override fun likeById(id: Long) {
        daoRoom.likeById(id)
    }

    override fun shareById(id: Long) {
        daoRoom.shareById(id)
    }

    override fun save(post: Post) {
        daoRoom.save(PostEntity.fromDto(post))
    }

    override fun removeById(id: Long) {
        daoRoom.removeById(id)
    }
}