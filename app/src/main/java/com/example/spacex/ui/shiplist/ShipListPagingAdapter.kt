package com.example.spacex.ui.shiplist

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
import com.example.spacex.model.Ship

class ShipListPagingAdapter
    : PagingDataAdapter<Ship, ShipListPagingAdapter.ShipViewHolder>(SHIP_COMPARATOR  ) {

    companion object {
        private val SHIP_COMPARATOR = object : DiffUtil.ItemCallback<Ship>() {
            override fun areItemsTheSame(oldItem: Ship, newItem: Ship) =
                oldItem.shipId == newItem.shipId

            override fun areContentsTheSame(oldItem: Ship, newItem: Ship) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        return ShipViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.row_ship_item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(getItem(position)?.image)
            .placeholder(R.drawable.ic_baseline_image_not_supported)
            .error(R.drawable.ic_baseline_image_not_supported)
            .into(holder.shipImage)

        holder.shipLegacyName.text = getItem(position)?.legacy
        holder.itemView.setOnClickListener {
            getItem(position)?.let {
                    launch ->
                onLaunchClickListener.invoke(launch)
            }
        }
    }

    class ShipViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val shipLegacyName: TextView = view.findViewById(R.id.ship_legacy_name)
        val shipImage: ImageView = view.findViewById(R.id.ship_image)
    }

    private lateinit var onLaunchClickListener : (ship : Ship) -> Unit
    fun setOnLaunchClickListener(listener:(ship : Ship) -> Unit){
        onLaunchClickListener = listener
    }
}