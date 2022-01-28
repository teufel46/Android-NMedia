package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.adaptor.PostAdaptor
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adaptor = PostAdaptor(
            likeClickListener =
            {
                viewModel.likeById(it.id)
            },
            shareClickListener =
            {
                viewModel.shareById(it.id)
            },
            onRemoveListener =
            {
                viewModel.removeById(it.id)
            },
        )

        binding.root.adapter = adaptor

        viewModel.data.observe(this, adaptor::submitList)
    }
}

