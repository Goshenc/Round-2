package com.example.bili

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bili.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("up_name") ?: "未知"
        val imageResId = intent.getIntExtra("up_image", R.drawable.cc)
        val fans = intent.getIntExtra("up_fans", 0)

        binding.upName.text = name
        binding.upFans.text = "粉丝数：$fans"
        binding.upImage.setImageResource(imageResId)

        binding.unfollowButton.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("position", intent.getIntExtra("position", -1))
            }
            setResult(RESULT_OK, resultIntent)
            Toast.makeText(this, "取关成功", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
