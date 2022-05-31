package com.nepplus.android_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nepplus.android_keepthetime.R
import com.nepplus.android_keepthetime.databinding.ListItemPlaceBinding
import com.nepplus.android_keepthetime.models.PlaceData

class PlaceRecyclerAdapter(
    val mContext : Context,
    val mList : List<PlaceData>
) : RecyclerView.Adapter<PlaceRecyclerAdapter.ItemViewHolder>() {

    lateinit var binding : ListItemPlaceBinding

    inner class  ItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind (item : PlaceData){
            binding.placeNameTxt.text = item.name
            if(!item.isPrimary){
                binding.isPrimaryTxt.visibility = View.GONE
            }

            binding.viewPalceMapImg.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.list_item_place, parent, false)
        return ItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}