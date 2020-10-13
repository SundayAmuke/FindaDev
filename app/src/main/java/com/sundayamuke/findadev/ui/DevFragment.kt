package com.sundayamuke.findadev.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.databinding.FragmentDevBinding

class DevFragment : Fragment() {

    private lateinit var binding: FragmentDevBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dev, container, false)

        return binding.root
    }

}