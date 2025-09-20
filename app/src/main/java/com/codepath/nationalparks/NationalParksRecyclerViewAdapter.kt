package com.codepath.nationalparks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NationalParksRecyclerViewAdapter(
    private val items: List<NationalPark>,
    // The starter repo defines this interface; ok to pass `this` from the Fragment
    private val listener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<NationalParksRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val nameTv: TextView = root.findViewById(R.id.park_name)
        val locationTv: TextView = root.findViewById(R.id.park_location)
        val descTv: TextView = root.findViewById(R.id.park_description)
        val imageIv: ImageView = root.findViewById(R.id.park_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_national_park, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val park = items[position]
        holder.nameTv.text = park.name ?: ""
        holder.locationTv.text = park.location ?: ""
        holder.descTv.text = park.description ?: ""

        Glide.with(holder.root)
            .load(park.imageUrl)
            .centerCrop()
            .into(holder.imageIv)

        holder.root.setOnClickListener { listener?.onItemClick(park) }
    }

    override fun getItemCount(): Int = items.size
}
