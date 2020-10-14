package com.sundayamuke.findadev.utils

import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.sundayamuke.findadev.R
import com.sundayamuke.findadev.adapters.MainAdapter
import com.sundayamuke.findadev.extensions.setSpinnerInverseBindingListener
import com.sundayamuke.findadev.model.Dev

@BindingAdapter("submitData")
fun RecyclerView.bindData(data: List<Dev> ?) {
    if(adapter != null) {
        val adapter = adapter as MainAdapter

        adapter.addHeaderAndSubmitList(data)
    }
}

@BindingAdapter("selectedValue")
fun Spinner.setValue(position: Int) {
    // Important to break potential infinite loops
    if (getSelectedValue() != position) {
        tag = position
        setSelection(position)
    }
}

@BindingAdapter("contentText")
fun TextView.setContentText(s: String) {
    text = when(s) {
        "" -> context.getString(R.string.info_needed_text)
        else -> s
    }
}

@BindingAdapter("index", "array")
fun TextView.setText(index: Int, array: Array<String>) {
    text = when(index) {
        -1 -> context.getString(R.string.info_needed_text)
        else -> array[index]
    }
}

@BindingAdapter("stack", "jobType")
fun TextView.setProfileText(stackIndex: Int, jobTypeIndex: Int) {
    var format = context.getString(R.string.profile_format)

    val stack = when(stackIndex) {
        -1 -> {
            format = format.replace(", ", "")
            ""
        }
        else -> context.resources.getStringArray(R.array.stack_options)[stackIndex]
    }
    val jobType = when(jobTypeIndex) {
        -1 -> {
            format = format.replace(", ", "")
            ""
        }
        else -> context.resources.getStringArray(R.array.job_type_options)[stackIndex]
    }

    text = format.format(stack, jobType)
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.getSelectedValue(): Int {
    return this.selectedItemPosition
}

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
    setSpinnerInverseBindingListener(inverseBindingListener)
}