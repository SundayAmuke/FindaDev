package com.sundayamuke.findadev.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.adapters.ItemClickListener
import com.sundayamuke.findadev.adapters.MainAdapter
import com.sundayamuke.findadev.databinding.FragmentMainBinding
import com.sundayamuke.findadev.model.Dev
import com.sundayamuke.findadev.viewmodel.MainViewModel
import com.sundayamuke.findadev.viewmodel.RC_SIGN_IN

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

        binding.lifecycleOwner = this

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.recyclerView.adapter = MainAdapter(viewModel.user.value!!, clickListener)

        viewModel.authListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user == null) {
                // Not signed in
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(viewModel.providers)
                        .build(),
                    RC_SIGN_IN)
            } else {
                onSignedInSetup(user)
            }
        }

        return binding.root
    }

    private fun onSignedInSetup(user: FirebaseUser) {
        viewModel.setupSignIn(user)
        binding.viewModel = viewModel
    }

    private val clickListener = ItemClickListener(
        profileClickListener = {user: Dev ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToProfileFragment(user)
            )
        }, devClickListener = {dev: Dev ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDevFragment(dev)
            )
        }
    )

    override fun onResume() {
        super.onResume()
        viewModel.addAuthListener()
    }

    override fun onPause() {
        super.onPause()
        viewModel.removeAuthListener()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}