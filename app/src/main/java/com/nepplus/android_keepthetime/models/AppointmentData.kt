package com.nepplus.android_keepthetime.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class AppointmentData(
    val id : Int,
    val title : String,
    val datetime : Date,
    @SerializedName("start_place")
    val startPlace : String,
    @SerializedName("start_latitude")
    val startLat : Double,
    @SerializedName("start_longitude")
    val startLong : Double,
    val place : String,
    val latitude : Double,
    val longitude : Double,
    val user : UserData,
    @SerializedName("invited_friends")
    val invitedFriends : List<UserData>

) {
}