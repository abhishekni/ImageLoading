package com.abhishekn.techm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abhishekn.techm.R
import com.abhishekn.techm.model.Row
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ImageAdapter(private val mContext : Context?, private val row : List<Row>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun getItemCount(): Int = row.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = row[position]

//        if (currentItem.title)
        holder.title.text           = currentItem.title
        holder.description.text     = currentItem.description

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        Glide.with(mContext)
            .load(currentItem.imageHref)
            .apply(options)
            .into(holder.image)

    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val title : TextView        = itemView.findViewById(R.id.title)
        val description : TextView  = itemView.findViewById(R.id.description)
        val image : ImageView       = itemView.findViewById(R.id.image)

    }
}