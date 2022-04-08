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
import com.mankart.mankgram.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()
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

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        initRecycler()
        return binding.root
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
        listStoryAdapter.setData(data)
        binding.rvStory.adapter = listStoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}