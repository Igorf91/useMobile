package com.igorf.contacts.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ContactVo(
    val id: Int,
    val name: String,
    val photo: String,
    val email: String,
    val isVerified: Boolean
) : Parcelable