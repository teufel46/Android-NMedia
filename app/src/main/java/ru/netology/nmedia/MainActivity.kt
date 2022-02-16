package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.*
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adaptor.ActionListener
import ru.netology.nmedia.adaptor.PostAdaptor
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adaptor = PostAdaptor(
            object : ActionListener{
                override fun onLikeClick(post: Post) {
                    viewModel.likeById(post.id)
                }

                override fun onShareClick(post: Post) {
                    viewModel.shareById(post.id)
                }

                override fun onRemoveClick(post: Post) {
                    viewModel.removeById(post.id)
                }

                override fun onEditClick(post: Post) {
                    viewModel.edit(post)
                }

                override fun onCancelEditClick(post: Post) {
                    viewModel.cancelEdit()
                }
            }
        )

        binding.list.adapter = adaptor

        viewModel.data.observe(this, adaptor::submitList)

        viewModel.edited.observe(this){
            if (it.id == 0L) {
                return@observe
            }
            with(binding.editedField){
                requestFocus()
                setText(it.content)
            }
            with(binding.oldContent){
                setText(it.content)
            }

            with(binding.oldContentGroup){
                if (binding.oldContent.text!=null) {
                    visibility = VISIBLE
                } else
                    visibility = GONE
            }
        }

        binding.saveButton.setOnClickListener {
            val text = binding.editedField.text?.toString()
            if (text.isNullOrBlank()) {
                Toast.makeText(this, getString(R.string.toastSaveMessage), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContent(text)
            viewModel.save()

            binding.editedField.clearFocus()
            binding.editedField.setText("")
            binding.oldContent.text = ""
            binding.oldContentGroup.visibility = GONE

            AndroidUtils.hideKeyboard(binding.editedField)
        }

        binding.cancelButton.setOnClickListener {
            binding.editedField.clearFocus()
            binding.editedField.setText("")
            binding.oldContent.text = ""
            binding.oldContentGroup.visibility = GONE

            AndroidUtils.hideKeyboard(binding.editedField)
        }
    }
}

