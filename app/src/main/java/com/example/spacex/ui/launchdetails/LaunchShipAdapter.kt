package com.example.spacex.ui.launchdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.model.Ship
import kotlinx.android.synthetic.main.row_launch_ship_item.view.*

class LaunchShipAdapter: ListAdapter<Ship, LaunchShipAdapter.LaunchShipViewHolder>(SHIP_COMPARATOR) {

    companion object {
        private val SHIP_COMPARATOR = object : DiffUtil.ItemCallback<Ship>() {
            override fun areItemsTheSame(oldItem: Ship, newItem: Ship) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Ship, newItem: Ship) =
                oldItem == newItem
        }
    }

    var onItemClickListener: ((Ship) -> Unit)? = null

    fun setOnClick(listener: ((Ship) -> Unit)) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: LaunchShipViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchShipViewHolder {
        return LaunchShipViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_launch_ship_item, parent, false)
        )
    }

    inner class LaunchShipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            with(itemView) {

                Glide.with(this)
                    .load(getItem(position).image)
                    .error(R.drawable.ic_baseline_image_not_supported)
                    .placeholder(R.drawable.ic_baseline_image_not_supported)
                    .into(launchShipImage)

                launchShipTitle.text = getItem(position).legacy

                itemView.setOnClickListener {
                    onItemClickListener?.invoke(getItem(position))
                }
            }
        }
    }
}

