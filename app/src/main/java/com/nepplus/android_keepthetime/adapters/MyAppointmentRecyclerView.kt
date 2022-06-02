package com.nepplus.android_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.nepplus.android_keepthetime.databinding.ListItemAppointmentBinding
import com.nepplus.android_keepthetime.models.AppointmentData
import java.text.SimpleDateFormat

class MyAppointmentRecyclerView(
    val mContext : Context,
    val mList : List<AppointmentData>,

) : RecyclerView.Adapter<MyAppointmentRecyclerView.ItemViewHolder>(){

    inner class ItemViewHolder(val binding : ListItemAppointmentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : AppointmentData){
            binding.titleTxt.text = item.title

            val sdf = SimpleDateFormat("M/d a h:mm")

            binding.timeTxt.text = "${sdf.format(item.datetime)}"
            binding.placeNameTxt.text = "약속장소 : ${item.place}"
            binding.memberCountTxt.text = "참여인원 : ${item.invitedFriends.size} 명"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemAppointmentBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}