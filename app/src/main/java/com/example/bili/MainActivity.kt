package com.example.bili

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bili.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val upList = ArrayList<Up>()
    private lateinit var upAdapter: UpAdapter
    private lateinit var viewPager2Adapter: ViewPager2Adapter

    // 注册 ActivityResultContracts 处理返回结果
    private val unfollowResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val position = result.data?.getIntExtra("position", -1) ?: return@registerForActivityResult
            if (position >= 0 && position < upList.size) {
                upList.removeAt(position)
                upAdapter.notifyItemRemoved(position)


                if (binding.viewpager2.currentItem >= upList.size) {
                    binding.viewpager2.currentItem = upList.size - 1
                }


                viewPager2Adapter.notifyDataSetChanged()
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

        // 初始化 ViewPager2
        viewPager2Adapter = ViewPager2Adapter(upList)
        binding.viewpager2.adapter = viewPager2Adapter

        // 初始化 RecyclerView
        setupRecyclerView()
    }

    private fun initUps() {
        // 初始化 up 数据
        upList.addAll(DataSender.createData())
    }

    private fun setupRecyclerView() {
        // 设置 RecyclerView 的布局管理器
        val layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.recyclerView.layoutManager = layoutManager

        // 初始化 RecyclerView 适配器
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
