package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adaptor.ActionListener
import ru.netology.nmedia.adaptor.PostAdaptor
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val newPostContract = registerForActivityResult(NewPostActivity.Contract()) { result ->
            result?.let {
                viewModel.changeContent(it)
                viewModel.save()
            }
        }

        val adaptor = PostAdaptor(
            object : ActionListener {
                override fun onLikeClick(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClick(post: Post) {
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }

                    val shareIntent =
                        Intent.createChooser(intent, getString(R.string.chooser_share_post))
                    startActivity(shareIntent)
                }

                override fun onRemoveClick(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEditClick(post: Post) {
                    viewModel.edit(post)
                    newPostContract.launch(post.content)
                }

                override fun onPlayMedia(post: Post) {
                    val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoURL))
                    if (playIntent.resolveActivity(packageManager) != null) {
                        startActivity(playIntent)
                    }

                }
            }
        )

        binding.list.adapter = adaptor

        viewModel.data.observe(this, adaptor::submitList)

        binding.addButton.setOnClickListener {
            newPostContract.launch("")
        }
    }
}

