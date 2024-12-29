package com.example.bili.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bili.adapter.UpAdapter
import com.example.bili.adapter.ViewPager2Adapter
import com.example.bili.data.DataSender
import com.example.bili.databinding.ActivityMainBinding
import com.example.bili.model.Up

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val upList = ArrayList<Up>()
    private lateinit var upAdapter: UpAdapter
    private lateinit var viewPager2Adapter: ViewPager2Adapter

    private val unfollowResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val position = result.data?.getIntExtra("position", -1) ?: return@registerForActivityResult
            if (position >= 0 && position < upList.size) {
                // 移除数据
                upList.removeAt(position)

                // 禁用 RecyclerView 动画，防止错位
                binding.recyclerView.itemAnimator = null

                // 通知适配器更新
                upAdapter.notifyItemRemoved(position)
                upAdapter.notifyDataSetChanged()
                viewPager2Adapter.notifyDataSetChanged()

                // 调整 ViewPager2 的 currentItem
                if (upList.isEmpty()) {
                    binding.viewpager2.currentItem = 0
                } else if (binding.viewpager2.currentItem >= upList.size) {
                    binding.viewpager2.currentItem = upList.size - 1
                }

                Toast.makeText(this, "已取关该up主", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "取关失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUps()


        viewPager2Adapter = ViewPager2Adapter(upList)
        binding.viewpager2.adapter = viewPager2Adapter


        setupRecyclerView()
    }

    private fun initUps() {
        upList.addAll(DataSender.createData())
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager

        upAdapter = UpAdapter(upList, ::onUpSelected, ::onUpLongPressed)
        binding.recyclerView.adapter = upAdapter
    }

    private fun onUpSelected(position: Int) {
        binding.viewpager2.currentItem = position
    }

    private fun onUpLongPressed(position: Int) {
        if (position < 0 || position >= upList.size) return
        val up = upList[position]

        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("up_name", up.name)
            putExtra("up_image", up.imageResId)
            putExtra("up_fans", up.fans)
            putExtra("position", position)
        }

        unfollowResultLauncher.launch(intent)
    }
}
