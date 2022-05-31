package com.nepplus.android_keepthetime.models

class DataResponse (
    val user : UserData,
    val token : String,
    val users : List<UserData>,
    val friends : List<UserData>,
    val places : List<PlaceData>
) {
}