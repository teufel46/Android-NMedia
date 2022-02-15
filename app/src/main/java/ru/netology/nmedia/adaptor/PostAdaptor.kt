package ru.netology.nmedia.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

typealias OnLikeClickListener = (Post) -> Unit
typealias OnShareClickListener = (Post) -> Unit
typealias OnRemoveListener = (Post) -> Unit

fun convertCount2String(count: Long): String {
    val result = when {
        count < 1_000 -> count.toString()
        count in 1_000..1_099 -> "1K"
        count in 1_100..9_999 -> String.format("%.1fK", (count / 100).toDouble() / 10)
        count in 10_000..999_999 -> (count / 1000).toString() + "K"
        count in 1_000_000..1_999_999 -> "1M"
        else -> String.format("%.1fМ", (count / 100_000).toDouble() / 10)
    }
    return result
}

class PostAdaptor(
    private val onLikeClickListener: OnLikeClickListener,
    private val onShareClickListener: OnShareClickListener,
    private val onRemoveListener: OnRemoveListener,
) : ListAdapter<Post, PostViewHolder>(PostDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            binding = CardPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onLikeClickListener = onLikeClickListener,
            onShareClickListener = onShareClickListener,
            onRemoveListener = onRemoveListener
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeClickListener: OnLikeClickListener,
    private val onShareClickListener: OnShareClickListener,
    private val onRemoveListener: OnRemoveListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likedCount.text = convertCount2String(post.likedCount)
            sharedCount.text = convertCount2String(post.sharedCount)
            viewedCount.text = convertCount2String(post.viewedCount)
            if (post.likedByMe) {
                liked?.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                liked?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            liked?.setOnClickListener {
                onLikeClickListener(post)
            }
            shared?.setOnClickListener {
                onShareClickListener(post)
            }
            menu?.setOnClickListener {
                PopupMenu(binding.root.context, binding.menu).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.remove -> {
                                onRemoveListener(post)
                                // если произошло удаление, то вызывает соотвт. коллбек
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class PostDiffItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}