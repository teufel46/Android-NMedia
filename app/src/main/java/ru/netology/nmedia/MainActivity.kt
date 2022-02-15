package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.adaptor.PostAdaptor
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adaptor = PostAdaptor(
            onLikeClickListener =
            {
                viewModel.likeById(it.id)
            },
            onShareClickListener =
            {
                viewModel.shareById(it.id)
            },
            onRemoveListener =
            {
                viewModel.removeById(it.id)
            },
        )

        binding.list.adapter = adaptor

        viewModel.data.observe(this, adaptor::submitList)

        binding.save.setOnClickListener {
            val text = binding.edited.text?.toString()
            if (text.isNullOrBlank()) {
                Toast.makeText(this, "Content empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContent(text)
            viewModel.save()

            binding.edited.clearFocus()
            binding.edited.setText("")
            AndroidUtils.hideKeyboard(binding.edited)
        }
    }
}

