package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adaptor.convertCount2String
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.util.CompanionArg.Companion.longArg
import ru.netology.nmedia.util.CompanionArg.Companion.textArg
import ru.netology.nmedia.viewmodel.PostViewModel

class PostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(layoutInflater)

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        with(binding.postContent) {
            viewModel.data.observe(viewLifecycleOwner) { posts ->
                val post = posts.find { it.id == arguments?.longArg }
                if (post != null) {
                    author.text = post.author
                    published.text = post.published
                    content.text = post.content
                    shared.text = convertCount2String(post.sharedCount)
                    viewed.text = convertCount2String(post.viewedCount)
                    liked?.isChecked = post.likedByMe
                    liked?.text = convertCount2String(post.likedCount)

                    if (post.videoURL != "") {
                        videoPreview.setImageResource(R.mipmap.ic_video_preview_foreground)
                        playButton.visibility = View.VISIBLE
                    } else {
                        playButton.visibility = View.INVISIBLE
                    }

                    liked?.setOnClickListener {
                        viewModel.likeById(post.id)
                    }

                    shared?.setOnClickListener {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, post.content)
                        }

                        val shareIntent =
                            Intent.createChooser(intent, getString(R.string.chooser_share_post))
                        startActivity(shareIntent)
                    }

                    menu?.setOnClickListener {
                        PopupMenu(binding.root.context, binding.postContent.menu).apply {
                            inflate(R.menu.post_menu)
                            setOnMenuItemClickListener {
                                when (it.itemId) {
                                    R.id.remove -> {
                                        findNavController().navigateUp()
                                        viewModel.removeById(post.id)
                                        true
                                    }
                                    R.id.edit -> {
                                        viewModel.edit(post)
                                        findNavController().navigate(R.id.action_postFragment_to_newPostFragment,
                                            Bundle().apply {
                                                textArg = post.content
                                            })
                                        true
                                    }
                                    else -> false
                                }
                            }
                        }.show()
                    }

                    playButton.setOnClickListener {
                        val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoURL))
                        if (playIntent.resolveActivity(requireContext().packageManager) != null) {
                            startActivity(playIntent)
                        }
                    }

                    videoPreview.setOnClickListener {
                        val playIntent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoURL))
                        if (playIntent.resolveActivity(requireContext().packageManager) != null) {
                            startActivity(playIntent)
                        }
                    }
                }
            }
        }
        return binding.root
    }
}

