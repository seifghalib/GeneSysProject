package com.example.genesysapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RandomUser(
    val nat: String,
    val name: UserName,
    val id: UserID,
    val picture: UserImageUrls
) : Parcelable {

    @Parcelize
    data class UserName(
        val title: String,
        val first: String,
        val last: String,
    ) : Parcelable

    @Parcelize
    data class UserID(val value: String) : Parcelable

    @Parcelize
    data class UserImageUrls(
        val large: String,
        val medium: String,
        val thumbnail: String
    ) : Parcelable
}