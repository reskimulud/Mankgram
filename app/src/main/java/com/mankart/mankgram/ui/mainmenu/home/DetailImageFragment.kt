package com.mankart.mankgram.ui.mainmenu.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.mankart.mankgram.R
import com.mankart.mankgram.databinding.FragmentDetailImageBinding

class DetailImageFragment : DialogFragment() {
    private var _binding: FragmentDetailImageBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val bundle = DetailImageFragmentArgs.fromBundle(arguments as Bundle)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )

        Glide.with(requireActivity())
            .load(bundle.image)
            .fitCenter()
            .into(binding.ivDetailImage)

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

        binding.root.setOnClickListener { dismiss() }
    }
}