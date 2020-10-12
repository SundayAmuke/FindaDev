package com.sundayamuke.findadev.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.InverseBindingListener
import com.sundayamuke.findadev.R


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

fun Spinner.getSelectedPosition(
    toFind: String
): Int {
    val arrayId = when (id) {
        R.id.spinnerJobType -> R.array.job_type_options
        R.id.spinnerStack -> R.array.stack_options
        else -> throw Exception("Unknown ID $id")
    }

    return context.resources.getStringArray(arrayId).indexOf(toFind)
}