package com.nepplus.android_keepthetime

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KeepTheTime : Application(){
    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, "ad9dd343bf99425487223aa79b95a4c8")
    }
}