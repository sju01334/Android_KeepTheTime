package com.nepplus.android_keepthetime.models

import com.google.gson.annotations.SerializedName

class DataResponse (
    val user : UserData,
    val token : String,
    val users : List<UserData>,
    val friends : List<UserData>,
    val places : List<PlaceData>,
    val appointment : AppointmentData,
    val appointments : List<AppointmentData>,
    @SerializedName("invited_appointments")
    val invitedAppointments : List<AppointmentData>,
) {
}