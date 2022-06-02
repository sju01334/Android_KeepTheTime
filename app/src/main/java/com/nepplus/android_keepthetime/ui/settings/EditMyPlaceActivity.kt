package com.nepplus.android_keepthetime.ui.settings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.ActivityEditMyPlaceBinding
import com.nepplus.android_keepthetime.models.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyPlaceActivity : BaseActivity() {

    lateinit var binding : ActivityEditMyPlaceBinding

    var mSelectedLatitude = 37.5779235853308
    var mSelectedLongitude = 127.033553463647


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_my_place)
        setupEvents()
        setValues()


    }

    override fun setupEvents() {
        binding.saveBtn.setOnClickListener {
            val inputName = binding.titleEdt.text.toString()
            if(inputName.isBlank()){
                Toast.makeText(mContext, "약속명을 기입해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiList.postReqeustAddMyPlace(inputName, mSelectedLatitude, mSelectedLongitude, false)
                .enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(mContext, "장소가 추가되었습니다", Toast.LENGTH_SHORT).show()
                            finish()
                        }else {
                            val errorBodyStr = response.errorBody()!!.string()
                            val jsonObj = JSONObject(errorBodyStr)
                            val code = jsonObj.getInt("code")
                            val message = jsonObj.getString("message")

                            if(code == 400){
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }else {
                                Log.d("장소 추가 서버 에러 : ", message)
                            }

                            
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        
                    }
                })
        }

    }

    override fun setValues() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync {
//        지도 로딩후, 온젆나 지도 객체
        val naverMap = it
//            시작지점
        val coord = LatLng(37.5779235853308, 127.033553463647)

        val cameraUpdate  =CameraUpdate.scrollTo(coord)
        naverMap.moveCamera(cameraUpdate)

        val marker = Marker()
        marker.position = coord
        marker.map = naverMap

        naverMap.setOnMapClickListener { pointF, latLng ->
            marker.position = latLng
            marker.map = naverMap
            mSelectedLatitude = latLng.latitude
            mSelectedLongitude = latLng.longitude
        }

        }
    }

    private fun practiceMap (){

////        지도 로딩후, 온젆나 지도 객체
//        val naverMap = it
//
////            시작지점
//        val coord = LatLng(37.5779235853308, 127.033553463647)
//
////            네이버 지도 카메라 이동
//        val cameraUpdate = CameraUpdate.scrollTo(coord)
//        naverMap.moveCamera(cameraUpdate)
//
////            첫 마터의 좌표
//        val marker = Marker()
//        marker.position = coord
//        marker.map = naverMap
//
////            지도 클릭 이벤트
//        naverMap.setOnMapClickListener { pointF, latLng ->
//            Log.d("클릭된 위도 경도", "위도 : ${latLng.latitude}, 경도 : ${latLng.longitude}")
//            val newMarker = Marker()
//            newMarker.position = latLng
//            newMarker.map = naverMap
//        }

    }
}