package com.example.favoritefoodplaces

import android.os.Parcel
import android.os.Parcelable

data class Restaurant(
    val name: String?,
    val address: String?,
    val typeOfFood: String?,
    val rating: Double,
    val imageResId: Int? = null,
    val info: String? = null
) : Parcelable {
























































    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()

        ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(typeOfFood)
        parcel.writeDouble(rating)
        if (imageResId != null) {
            parcel.writeInt(imageResId)
        }
        parcel.writeString(info)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}