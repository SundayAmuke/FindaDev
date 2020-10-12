package com.sundayamuke.findadev.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.adapters.ItemClickListener
import com.sundayamuke.findadev.adapters.MainAdapter
import com.sundayamuke.findadev.databinding.FragmentMainBinding
import com.sundayamuke.findadev.model.Dev
import com.sundayamuke.findadev.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.viewModel = viewModel

        binding.recyclerView.adapter = MainAdapter(user = Dev(), clickListener)

        binding.lifecycleOwner = this

        return binding.root
    }

    private val clickListener = ItemClickListener(
        profileClickListener = {dev: Dev ->
            Toast.makeText(context, dev.fullName, Toast.LENGTH_SHORT).show()
        }, devClickListener = {dev: Dev ->
            Toast.makeText(context, dev.fullName, Toast.LENGTH_SHORT).show()
        }
    )
}