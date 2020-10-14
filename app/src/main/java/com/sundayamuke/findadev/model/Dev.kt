package com.sundayamuke.findadev.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dev(
    var fullName: String = "",
    var email: String = "",
    var photoUrl: String = "",
    var portfolioLink: String = "",
    var stack: Int = -1,
    var jobType: Int = -1,
    var technologies: String = ""
) : Parcelable