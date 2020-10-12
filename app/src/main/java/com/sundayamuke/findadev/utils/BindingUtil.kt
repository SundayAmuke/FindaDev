package com.sundayamuke.findadev.utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.sundayamuke.findadev.extensions.getSelectedPosition
import com.sundayamuke.findadev.extensions.setSpinnerInverseBindingListener


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