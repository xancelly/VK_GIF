package com.example.vkgif.presentation.fragments.detail_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.vkgif.R
import com.example.vkgif.databinding.FragmentDetailBinding
import com.example.vkgif.domain.models.Data

class DetailFragment : Fragment() {

    private var detailBinding: FragmentDetailBinding? = null
    private val binding get() = detailBinding!!
    private val dataArgs: DetailFragmentArgs by navArgs()
    private lateinit var data: Data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        val callback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val direction = DetailFragmentDirections.actionDetailFragmentToSearchFragment()
                findNavController().navigate(direction)
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = dataArgs.data
        getDataFromArgs()
    }

    private fun getDataFromArgs() {
        binding.apply {
            Glide.with(binding.root)
                .load(data.images.original.url)
                .into(gifImageView)
            Glide.with(binding.root)
                .load(data.user.avatar_url)
                .into(authorAvatarImageView)
            gifWidthTextView.text = data.images.original.width
            gifHeightTextView.text = data.images.original.height
            titleTextView.text = data.title
            usernameTextView.text = data.user.display_name
            importDataTextView.text = data.import_datetime
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }
}