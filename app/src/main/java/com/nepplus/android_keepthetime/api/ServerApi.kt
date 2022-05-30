package com.nepplus.android_keepthetime.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerApi {

    companion object{

//        서버주소
        private val baseUrl = "https://keepthetime.xyz"

        private var retrofit : Retrofit? = null

        fun getRetrofit() : Retrofit {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}