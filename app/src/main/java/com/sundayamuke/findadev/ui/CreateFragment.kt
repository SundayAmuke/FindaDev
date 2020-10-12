package com.sundayamuke.findadev.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.databinding.FragmentCreateBinding
import com.sundayamuke.findadev.viewmodel.CreateViewModel

class CreateFragment : Fragment() {

    private lateinit var binding: FragmentCreateBinding

    private val viewModel: CreateViewModel by lazy {
        ViewModelProvider(this)[CreateViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create, container, false)

        binding.dev = viewModel.dev

        binding.lifecycleOwner = this

        binding.btnSubmit.setOnClickListener {
            Log.i("CreateFragment", viewModel.dev.toString())
        }

        return binding.root
    }

    companion object {
        fun newInstance() : CreateFragment = CreateFragment()
    }


}