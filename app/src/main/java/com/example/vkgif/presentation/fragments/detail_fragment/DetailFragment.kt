package com.example.vkgif.presentation.fragments.detail_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
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
        //Обработка события при нажатии кнопки "Назад" на телефоне, после чего идёт перенаправление на SearchFragment
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

    //Функция получения данных из аргументов, переданных из другого фрагмента
    private fun getDataFromArgs() {
        binding.apply {
            Glide.with(binding.root)
                .load(data.images.original.url)
                .into(gifImageView)
            if (data.user != null) {
                Glide.with(binding.root)
                    .load(data.user!!.avatar_url)
                    .into(authorAvatarImageView)
            } else {
                authorAvatarImageView.visibility = View.GONE
            }
            gifWidthTextView.text = data.images.original.width
            gifHeightTextView.text = data.images.original.height
            titleTextView.text = data.title
            usernameTextView.text = data.username
            importDataTextView.text = data.import_datetime
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        detailBinding = null
    }
}