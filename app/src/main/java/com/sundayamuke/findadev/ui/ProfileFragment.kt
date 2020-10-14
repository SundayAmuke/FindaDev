package com.sundayamuke.findadev.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val args: ProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.fab.setOnClickListener (
            Navigation.createNavigateOnClickListener(
                ProfileFragmentDirections.actionProfileFragmentToEditFragment(args.user)
            )
        )

        binding.user = args.user

        return binding.root
    }
}