package com.mankart.mankgram.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mankart.mankgram.ListStoryAdapter
import com.mankart.mankgram.StoryModel
import com.mankart.mankgram.ViewModelFactory
import com.mankart.mankgram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by activityViewModels { factory }
    private var _binding: FragmentHomeBinding? = null
    private lateinit var listStoryAdapter: ListStoryAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        homeViewModel.getName().observe(viewLifecycleOwner) {
            binding.tvWelcomeName.text = it
        }

        homeViewModel.getUserStories()

        initRecycler()
    }

    private fun initRecycler() {
        val data: ArrayList<StoryModel> = arrayListOf(
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
            StoryModel(name = "Reski Mulud Muchamad"),
        )

        binding.rvStory.layoutManager = LinearLayoutManager(activity)
        listStoryAdapter = ListStoryAdapter()
//        listStoryAdapter.setData(data)
        homeViewModel.userStories.observe(viewLifecycleOwner) {
            listStoryAdapter.setData(it)
        }
        binding.rvStory.adapter = listStoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}