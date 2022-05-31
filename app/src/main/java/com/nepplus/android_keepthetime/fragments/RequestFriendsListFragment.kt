package com.nepplus.android_keepthetime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.adapters.MyFriendsRecyclerAdapter
import com.nepplus.android_keepthetime.databinding.FragmentRequestFriendsListBinding
import com.nepplus.android_keepthetime.models.BasicResponse
import com.nepplus.android_keepthetime.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestFriendsListFragment : BaseFragment() {

    lateinit var binding : FragmentRequestFriendsListBinding

    lateinit var mFriendsAdapter : MyFriendsRecyclerAdapter

    var mFriendList = ArrayList<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_friends_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getRequestedFriendsListFromServer()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mFriendsAdapter = MyFriendsRecyclerAdapter(mContext, mFriendList,"requested")
        binding.myFriendsRecyclerView.adapter = mFriendsAdapter
        binding.myFriendsRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getRequestedFriendsListFromServer(){
        apiList.getRequestMyFriendsList("requested").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if(response.isSuccessful){
                    val br = response.body()!!
                    mFriendList.clear()
                    mFriendList.addAll(br.data.friends)

                    mFriendsAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}