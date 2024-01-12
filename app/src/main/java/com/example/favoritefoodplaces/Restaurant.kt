package com.example.favoritefoodplaces

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler


@kotlinx.parcelize.Parcelize
data class Restaurant(
    val name: String? = null,
    val address: String? = null,
    val typeOfFood: String? = null,
    val rating: Double = 0.0,
    val imageResId: Int? = null,
    val info: String? = null
) : Parcelable {
    constructor() : this("", "", "", 0.0, 0, "")

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString()
    )

    companion object : Parceler<Restaurant> {

        override fun Restaurant.write(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(address)
            parcel.writeString(typeOfFood)
            parcel.writeDouble(rating)
            if (imageResId != null) {
                parcel.writeInt(imageResId)
            }
            parcel.writeString(info)
        }

        override fun create(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }
    }
}
