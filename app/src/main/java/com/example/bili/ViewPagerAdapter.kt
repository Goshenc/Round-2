package com.example.bili

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bili.databinding.ItemDynamicBinding

class ViewPager2Adapter(private val upList: List<Up>) : RecyclerView.Adapter<ViewPager2Adapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemDynamicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDynamicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val up = upList[position]
        holder.binding.dynamicImage.setImageResource(up.imageResId)
        holder.binding.dynamicDescription.text = "这是 ${up.name} 的动态。"
    }

    override fun getItemCount() = upList.size
}
