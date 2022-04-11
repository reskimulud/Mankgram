package com.mankart.mankgram.ui.mainmenu.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mankart.mankgram.ui.adapter.ListStoryAdapter
import com.mankart.mankgram.ui.ViewModelFactory
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

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = true
            homeViewModel.getUserStories()
        }

        binding.refreshLayout.isRefreshing = true
        homeViewModel.getUserStories()

        initObserve()
        initRecycler()
    }

    private fun initObserve() {
        homeViewModel.getName().observe(viewLifecycleOwner) {
            binding.tvWelcomeName.text = it
        }
        homeViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecycler() {
        binding.rvStory.layoutManager = LinearLayoutManager(activity)
        listStoryAdapter = ListStoryAdapter()
        homeViewModel.userStories.observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = false
            listStoryAdapter.setData(it)
        }
        binding.rvStory.adapter = listStoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}