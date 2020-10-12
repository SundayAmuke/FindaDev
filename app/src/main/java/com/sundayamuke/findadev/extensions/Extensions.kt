package com.sundayamuke.findadev.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.InverseBindingListener


fun Spinner.setSpinnerInverseBindingListener(listener: InverseBindingListener?) {
    onItemSelectedListener = if (listener == null) {
        null
    } else {
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (tag != position) {
                    listener.onChange()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
