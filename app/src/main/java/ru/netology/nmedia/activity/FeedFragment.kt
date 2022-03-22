package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adaptor.ActionListener
import ru.netology.nmedia.adaptor.PostAdaptor
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

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
                    findNavController().navigate(R.id.action_feedFragment_to_newPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        })
                }

                override fun onPlayMedia(post: Post) {
                   val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoURL))
                       //  if (playIntent.resolveActivity(packageManager) != null) {
                        startActivity(playIntent)
                }
            }
        )

        binding.list.adapter = adaptor

        viewModel.data.observe(viewLifecycleOwner, adaptor::submitList)

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }

}

