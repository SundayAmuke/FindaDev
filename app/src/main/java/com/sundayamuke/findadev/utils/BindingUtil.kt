package com.sundayamuke.findadev.utils

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.sundayamuke.findadev.adapters.MainAdapter
import com.sundayamuke.findadev.extensions.setSpinnerInverseBindingListener
import com.sundayamuke.findadev.model.Dev

@BindingAdapter("submitData")
fun RecyclerView.bindData(data: List<Dev> ?) {
    val adapter = adapter as MainAdapter

    adapter.addHeaderAndSubmitList(data)
}

@BindingAdapter("selectedValue")
fun Spinner.setValue(position: Int) {
    // Important to break potential infinite loops
    if (getSelectedValue() != position) {
        tag = position
        setSelection(position)
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.getSelectedValue(): Int {
    return this.selectedItemPosition
}

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
    setSpinnerInverseBindingListener(inverseBindingListener)
}