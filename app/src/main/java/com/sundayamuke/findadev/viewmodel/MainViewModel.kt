package com.sundayamuke.findadev.viewmodel

import androidx.lifecycle.ViewModel
import com.sundayamuke.findadev.model.Dev

class MainViewModel: ViewModel() {
    val devs = listOf<Dev>(
        Dev(fullName = "Oluwafemi Juri", stack = 3),
        Dev(fullName = "Mike King", stack = 5),
        Dev(fullName = "Omilo Samuel", stack = 0),
        Dev(fullName = "Tomisin", stack = 3),
        Dev(fullName = "Tofunmi", stack = 3)
    )


}