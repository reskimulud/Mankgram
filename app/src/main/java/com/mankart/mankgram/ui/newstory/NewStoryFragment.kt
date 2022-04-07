package com.mankart.mankgram.ui.newstory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mankart.mankgram.databinding.FragmentNewStoryBinding

class NewStoryFragment : Fragment() {
    private val newStoryViewModel: NewStoryViewModel by activityViewModels()
    private var _binding: FragmentNewStoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewStoryBinding.inflate(inflater, container, false)

        newStoryViewModel.text.observe(viewLifecycleOwner) {
            binding.textNewStory.text = it
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}