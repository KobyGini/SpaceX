package com.example.spacex.ui.launcheslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.data.local.model.LaunchLocal

class LaunchPagingAdapter
    : PagingDataAdapter<LaunchLocal, LaunchPagingAdapter.LaunchViewHolder>(LAUNCH_COMPARATOR) {

    companion object {
        private val LAUNCH_COMPARATOR = object : DiffUtil.ItemCallback<LaunchLocal>() {
            override fun areItemsTheSame(oldItem: LaunchLocal, newItem: LaunchLocal) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: LaunchLocal, newItem: LaunchLocal) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.row_launch_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        Glide.with(holder.itemView).load(getItem(position)?.missionPatch).into(holder.launchImage)
        holder.launchName.text = getItem(position)?.missionName
        holder.itemView.setOnClickListener {
            getItem(position)?.let {
                    launch ->
                onLaunchClickListener.invoke(launch)
            }
        }
    }

    class LaunchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val launchName: TextView = view.findViewById(R.id.launch_mission_name)
        val launchImage: ImageView = view.findViewById(R.id.launch_image)
    }

    private lateinit var onLaunchClickListener : (launchLocalModel : LaunchLocal) -> Unit
    fun setOnLaunchClickListener(listener:(launchLocalModel : LaunchLocal) -> Unit){
        onLaunchClickListener = listener
    }
}