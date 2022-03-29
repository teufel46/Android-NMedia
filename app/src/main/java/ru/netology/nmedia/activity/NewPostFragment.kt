package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.CompanionArg.Companion.textArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(layoutInflater)
        arguments?.textArg?.let {
            binding.content.setText(it)
        }
        binding.content.requestFocus()

        val viewModel: PostViewModel by viewModels(::requireParentFragment)

        binding.saveButton.setOnClickListener {
            if (!binding.content.text.isNullOrBlank()){
                val content = binding.content.text.toString()
                viewModel.changeContent(content)
                viewModel.save()
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

}