package com.nepplus.android_keepthetime.api

import com.nepplus.android_keepthetime.models.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIList {

    //    user
    @GET("/user/check")
    fun getReqeustUserCheck(
        @Query("type") type: String,
        @Query("value") value: String
    ): Call<BaseResponse>
}