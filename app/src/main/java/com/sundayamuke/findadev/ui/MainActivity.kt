package com.sundayamuke.findadev.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        openFragment()

        binding.bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.item_devs -> {
                val listFragment = ListFragment.newInstance()
                openFragment(listFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_create -> {
                val createFragment = CreateFragment.newInstance()
                openFragment(createFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment? = null) {

        val transaction = supportFragmentManager.beginTransaction()

        if (fragment == null) {
            transaction.replace(R.id.container, ListFragment())
        } else {
            transaction.replace(R.id.container, fragment)
        }

        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}