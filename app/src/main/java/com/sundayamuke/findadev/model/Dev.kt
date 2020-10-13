package com.sundayamuke.findadev.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dev(
    var fullName: String = "Amuke Sunday",
    var portfolioLink: String = "https://github.com/SundayAmuke",
    var stack: Int = 1,
    var jobType: Int = 1,
    var technologies: String = "Android, Java, Kotlin..."
) : Parcelable