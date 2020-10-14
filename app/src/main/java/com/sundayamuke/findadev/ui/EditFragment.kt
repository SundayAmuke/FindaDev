package com.sundayamuke.findadev.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.databinding.FragmentEditBinding
import com.sundayamuke.findadev.model.Dev
import com.sundayamuke.findadev.viewmodel.EditViewModel

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding

    private val args: EditFragmentArgs by navArgs()

    private val viewModel: EditViewModel by lazy {
        ViewModelProvider(this)[EditViewModel::class.java]
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.user = args.user

        binding.btnSave.setOnClickListener {
            viewModel.updateUser(binding.user as Dev)
        }

        viewModel.eventUpdateSuccessful.observe(viewLifecycleOwner, {
            if (it == true) {
                findNavController().navigate(
                    EditFragmentDirections.actionEditFragmentToProfileFragment(binding.user as Dev)
                )
            }
        })

        return binding.root
    }

}