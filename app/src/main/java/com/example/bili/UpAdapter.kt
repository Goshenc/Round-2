package com.example.bili

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bili.databinding.ItemUpBinding

class UpAdapter(
    private val upList: List<Up>,
    private val onItemClick: (Int) -> Unit,
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<UpAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUpBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val up = upList[position]
        holder.binding.upName.text = up.name
        holder.binding.upImage.setImageResource(up.imageResId)
        holder.binding.root.setOnClickListener { onItemClick(position) }
        holder.binding.root.setOnLongClickListener {
            if (position in 0 until upList.size) {
                onItemLongClick(position)
                true
            } else {
                false
            }
        }

    }

    override fun getItemCount() = upList.size
}
