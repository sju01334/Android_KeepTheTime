package com.nepplus.android_keepthetime.api

import com.nepplus.android_keepthetime.models.BasicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIList {

    //  search
    @GET("/search/user")
    fun getRequestSearchUser(@Query("nickname") nickname : String) : Call<BasicResponse>

    //    user
    @GET("/user")
    fun getRequestMyInfo(): Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserInfo(
        @Field("field") field: String,
        @Field("value") value: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") email: String,
        @Field("password") pw: String,
        @Field("nick_name") nickname: String,
    ): Call<BasicResponse>

    @GET("/user/check")
    fun getRequestUserCheck(
        @Query("type") type: String,
        @Query("value") value: String
    ): Call<BasicResponse>

    @Multipart
    @PUT("/user/image")
    fun putRequestUserImage(@Part profileImg: MultipartBody.Part): Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/social")
    fun postRequestSocialLogin(
        @Field("provider") provider : String,
        @Field("uid") uid : String,
        @Field("nick_name") nickname : String
    ) : Call<BasicResponse>

    //    user/friend
    @GET("/user/friend")
    fun getRequestMyFriendsList(@Query("type") type : String) : Call<BasicResponse>
//    유저에게 친구 추가를 보내는 API
    @FormUrlEncoded
    @POST("/user/friend")
    fun postRequestAddFriend(@Field ("user_id") userId : Int) : Call<BasicResponse>

    @FormUrlEncoded
    @PUT ("/user/friend")
    fun putRequestAnswerRequest(
        @Field ("user_id") userId : Int,
        @Field("type") type: String,
    ) : Call<BasicResponse>


//    place
    @GET("/user/place")
    fun getRequestMyPlace() : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user/place")
    fun postReqeustAddMyPlace(
        @Field("name") name : String,
        @Field("latitude") latitude : Double,
        @Field("longitude") longitude : Double,
        @Field("is_primary") isPrimary : Boolean,
    ) : Call<BasicResponse>

//    appointment
    @GET("/appointment")
    fun getRequestMyAppointment() : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/appointment")
    fun postRequestAddAppointment (
    @Field("title") title : String,
    @Field("datetime") datetime : String,
    @Field("start_place") startPlace : String,
    @Field("start_latitude") startLatitude : Double,
    @Field("start_longitude") startLongitude : Double,
    @Field("place") place  : String,
    @Field("latitude") latitude : Double,
    @Field("longitude") longitude : Double,
    @Field("friend_list") friendList : String,
    ) : Call<BasicResponse>
}
