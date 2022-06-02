package com.nepplus.android_keepthetime.ui.appointment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.nepplus.android_keepthetime.BaseActivity
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.adapters.MyFriendsSpinnerAdapter
import com.nepplus.android_keepthetime.adapters.StartPlaceSpinnerAdapter
import com.nepplus.android_keepthetime.databinding.ActivityEditAppointmentBinding
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.models.PlaceData
import com.nepplus.android_keepthetime.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : BaseActivity() {

    lateinit var binding : ActivityEditAppointmentBinding

    //    선택한 약속 일시를 저장할 멤버변수
    val mSelectedDateTime = Calendar.getInstance()  // 기본값 : 현재시간

    //    출발 장소를 담고있는 관련 변수
    var mStartPlaceList = ArrayList<PlaceData>()
    lateinit var mStartPlaceSpinnerAdapter : StartPlaceSpinnerAdapter
    lateinit var mSelectedStartPlace : PlaceData

    //    친구 목록을 담고 있는 Spinner 관련 변수
    var mFriendsList = ArrayList<UserData>()
    lateinit var mFriendsSpinnerAdapter: MyFriendsSpinnerAdapter

    //    네이버 지도 관련 변수
    var mNaverMap : NaverMap? = null
    var mStartPlaceMarker = Marker()  // 출발지에 표시될 하나의 마커
    var mSelectedPlaceMarker = Marker()  // 도착지에 표시될 하나의 마커
    var mSelectedLatLng : LatLng? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        binding.mapView.onCreate(savedInstanceState)
        setupEvents()
        setValues()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setupEvents() {

//            날짜 선택
        binding.dateTxt.setOnClickListener {
//            날짜를 선택하고 할 일(인터페이스)를 작성
            val dl = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                    mSelectedDateTime.set(year, month, day)

                    val sdf = SimpleDateFormat("yyyy. M. d (E)")
                    Log.d("선택된 시간", sdf.format(mSelectedDateTime.time))

                    binding.dateTxt.text = sdf.format(mSelectedDateTime.time)
                }
            }

//            DatePickerDialog 팝업
            val dpd = DatePickerDialog(
                mContext,
                dl,
                mSelectedDateTime.get(Calendar.YEAR),
                mSelectedDateTime.get(Calendar.MONTH),
                mSelectedDateTime.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show()
        }

//            시간 선택
        binding.timeTxt.setOnClickListener {
            val tsl = object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
                    mSelectedDateTime.set(Calendar.HOUR_OF_DAY, hour)
                    mSelectedDateTime.set(Calendar.MINUTE, minute)

//                    오후 12:10 형태로 가공
                    val sdf = SimpleDateFormat("a h:mm")
                    binding.timeTxt.text = sdf.format(mSelectedDateTime.time)

                }
            }

            TimePickerDialog(
                mContext,
                tsl,
                mSelectedDateTime.get(Calendar.HOUR_OF_DAY),
                mSelectedDateTime.get(Calendar.MINUTE),
                false
            ).show()
        }

//        시작장소 스피너 선택 이벤트
        binding.startPlaceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
//                Log.d("선택된 출발지 정보", position.toString())
                mSelectedStartPlace = mStartPlaceList[position]

                mNaverMap?.let {
                    mStartPlaceMarker.position = LatLng(mSelectedStartPlace.latitude, mSelectedStartPlace.longitude)
                    mStartPlaceMarker.map = mNaverMap

                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(mSelectedStartPlace.latitude, mSelectedStartPlace.longitude))
                    it.moveCamera(cameraUpdate)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        약속 추가 이벤트
        binding.addBtn.setOnClickListener {

//            1. 약속의 제목 정했는가
            val inputTitle = binding.titleEdt.text.toString()
            if (inputTitle.isBlank()) {
                Toast.makeText(mContext, "약속 제목을 정해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            2. 날짜/시간이 선택이 되었는가?
//             =>날짜 / 기간 중 선택 안한게 있다면, 선택하라고 토스트 함수를 강제 종료하자.
            if (binding.dateTxt.text == "일자 선택") {
                Toast.makeText(mContext, "약속 일자를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.timeTxt.text == "시간 선택") {
                Toast.makeText(mContext, "약속 시간을 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            지금시간과 선택된(mSelectedDateTime)과의 시간차를 계산
            if (mSelectedDateTime.timeInMillis < Calendar.getInstance().timeInMillis) {
                Toast.makeText(mContext, "현재 시간 이후의 시간으로 선택해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            3. 도착 지점을 선택했는가?
            if (mSelectedLatLng == null) {
                Toast.makeText(mContext, "도착지를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            3-1. 장소명을 기록했는가?
            val inputPlaceName = binding.placeNameEdt.text.toString()
            if (inputPlaceName.isBlank()) {
                Toast.makeText(mContext, "약속 장소명을 기입해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var friendListStr = ""
//            서버에서 요구한 약속 일시 형태 지정
            val sdf =SimpleDateFormat("yyyy-MM-dd HH:mm")

            apiList.postRequestAddAppointment(
                inputTitle,
                sdf.format(mSelectedDateTime.time),
                mSelectedStartPlace.name,
                mSelectedStartPlace.latitude,
                mSelectedStartPlace.longitude,
                inputPlaceName,
                mSelectedLatLng!!.latitude,
                mSelectedLatLng!!.longitude,
                friendListStr
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(mContext, "약속이 등록되었습니다", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                }
            })



        }

//        지도 영역에 손을 대면(setOnTouch) => 스크롤뷰를 정지
//        대안 : 지도위에 텍스트뷰를 겹쳐두고, 텍스트뷰에 손을 대면 => 스크롤뷰 정지
        binding.scrollHelpTxt.setOnTouchListener { view, motionEvent ->

            binding.scrollView.requestDisallowInterceptTouchEvent(true)

//            터치 이벤트만 먹히게 할거냐? => no. 뒤에 가려진 지도 동작도 같이 실행
            return@setOnTouchListener false
        }

    }

    override fun setValues() {
        titleTxt.text = "새 약속 만들기"

        mStartPlaceSpinnerAdapter = StartPlaceSpinnerAdapter(mContext, R.layout.list_item_place, mStartPlaceList)
        binding.startPlaceSpinner.adapter = mStartPlaceSpinnerAdapter

        mFriendsSpinnerAdapter = MyFriendsSpinnerAdapter(mContext, R.layout.list_item_user, mFriendsList)
        binding.invitedFriendSpinner.adapter = mFriendsSpinnerAdapter

        getMyPlaceListFromServer()
        getMyFriendsListFromServer()

        binding.mapView.getMapAsync {

            if (mNaverMap == null) {
                mNaverMap = it
            }

            mSelectedPlaceMarker.icon = OverlayImage.fromResource(R.drawable.red_marker)

            it.setOnMapClickListener { pointF, latLng ->
                mSelectedLatLng = latLng

                Log.d("선택된 좌표", "위도 : ${mSelectedLatLng?.latitude}, 경도 : ${mSelectedLatLng?.longitude}")

                mSelectedPlaceMarker.position = latLng
                mSelectedPlaceMarker.map = it
            }
        }
    }

    fun getMyPlaceListFromServer () {
        apiList.getRequestMyPlace().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

                    mStartPlaceList.clear()
                    mStartPlaceList.addAll(response.body()!!.data.places)
                    mStartPlaceSpinnerAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    fun getMyFriendsListFromServer () {
        apiList.getRequestMyFriendsList("my").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    mFriendsList.clear()
                    mFriendsList.addAll(response.body()!!.data.friends)

                    mFriendsSpinnerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}