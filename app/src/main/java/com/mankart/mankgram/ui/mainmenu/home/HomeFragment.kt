package com.mankart.mankgram.ui.mainmenu.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mankart.mankgram.R
import com.mankart.mankgram.ui.adapter.ListStoryAdapter
import com.mankart.mankgram.ui.ViewModelFactory
import com.mankart.mankgram.databinding.FragmentHomeBinding
import com.mankart.mankgram.ui.adapter.LoadingStateListStoryAdapter
import com.mankart.mankgram.ui.mapviewstory.MapViewStoryActivity

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
        binding.homeToolbar.inflateMenu(R.menu.map_view_option)

        binding.homeToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.map_view -> {
                    startActivity(Intent(activity, MapViewStoryActivity::class.java))
                }
            }
            true
        }

        factory = ViewModelFactory.getInstance(requireActivity())

        binding.refreshLayout.setOnRefreshListener {
            listStoryAdapter.refresh()
        }
        fetchUserStories()

        initObserve()
    }

    private fun fetchUserStories() {
        homeViewModel.getUserToken().observe(viewLifecycleOwner) {
            binding.refreshLayout.isRefreshing = true
            homeViewModel.getUserStories(it)
            initRecycler()
            Log.e("Home", "Token: $it")
        }
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
            listStoryAdapter.submitData(lifecycle, it)
        }
        binding.rvStory.adapter = listStoryAdapter.withLoadStateFooter(
            footer = LoadingStateListStoryAdapter { listStoryAdapter.retry() }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        listStoryAdapter.submitData(lifecycle, PagingData.empty())
    }
}