package com.nepplus.android_keepthetime.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.api.APIList
import com.nepplus.android_keepthetime.api.ServerApi
import com.nepplus.android_keepthetime.fragments.RequestFriendsListFragment
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.models.UserData
import com.nepplus.android_keepthetime.ui.settings.MyFriendsActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsRecyclerAdapter(
    val mContext: Context,
    val mList: ArrayList<UserData>,
    val type: String
) : RecyclerView.Adapter<MyFriendsRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val profileImg = view.findViewById<ImageView>(R.id.profileImg)
        val nicknameTxt = view.findViewById<TextView>(R.id.nicknameTxt)
        val addFriendBtn = view.findViewById<Button>(R.id.addFriendBtn)
        val acceptBtn = view.findViewById<Button>(R.id.acceptBtn)
        val denyBtn = view.findViewById<Button>(R.id.denyBtn)
        val requestBtnLayout = view.findViewById<LinearLayout>(R.id.requestBtnLayout)
        val socialLoginImg = view.findViewById<ImageView>(R.id.socialLoginImg)

        fun bind (item : UserData) {

            val apiList = ServerApi.getRetrofit(mContext).create(APIList::class.java)

            Glide.with(mContext).load(item.profileImg).into(profileImg)
            nicknameTxt.text = item.nickname

            when(item.provider){
                "kakao" ->{
                    socialLoginImg.setImageResource(R.drawable.kakao_login_icon)
                }
                "facebook" -> {
                    socialLoginImg.setImageResource(R.drawable.facebook_login_icon)
                }
                else -> {
                    socialLoginImg.visibility = View.GONE
                }
            }

            when (type) {
                "add" -> {
                    addFriendBtn.visibility = View.VISIBLE
                    requestBtnLayout.visibility = View.GONE
                }
                "requested" -> {
                    addFriendBtn.visibility = View.GONE
                    requestBtnLayout.visibility = View.VISIBLE
                }
                "my" -> {
                    addFriendBtn.visibility = View.GONE
                    requestBtnLayout.visibility = View.GONE
                }
            }

//            수락 / 거절 버튼 둘다 하는 일이 동일 => type에 들어갈 값만 다르다
//            버튼에 태그달아놓고 꺼내쓰자 => 동일한 작업

            val ocl = object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val okOrNO = p0!!.tag.toString()

//                    어댑터에서 API 서비스 사용법
//                    1. 직접 만들자
                    apiList.putRequestAnswerRequest(item.id, okOrNO).enqueue(object : Callback<BasicResponse>{
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (!response.isSuccessful) {
                                val errorBodyStr = response.errorBody()!!.string()
                                val jsonObj = JSONObject(errorBodyStr)
                                val message = jsonObj.getString("message")

                                Log.e("승인_거절 실패", message)
                            }

//                            프래그먼트의 요청목록(requested Friends List) 새로 받아오는 함수를 실행
//                            어댑터 -> 액티비티 : context  변수 활용

//                            ViewPager 2 에서 fragment 를 찾아서 새로 받아오는 함수 실행
//                            ViewPager2 에서는 내부의 fragment 를 지정할 수 없다 => tag 값으로 찾아온다
                            ((mContext as MyFriendsActivity)
                                .supportFragmentManager
                                .findFragmentByTag("f1") as RequestFriendsListFragment)
                                .getRequestedFriendsListFromServer()
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })
                }
            }

            acceptBtn.setOnClickListener(ocl)
            denyBtn.setOnClickListener(ocl)

            addFriendBtn.setOnClickListener {
                apiList.postRequestAddFriend(item.id).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                mContext,
                                "${item.nickname}님에게 친구요청을 보냈습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.list_item_user, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}