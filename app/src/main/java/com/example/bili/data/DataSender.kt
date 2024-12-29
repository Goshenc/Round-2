package com.example.bili.data

import com.example.bili.R
import com.example.bili.model.Up

object DataSender {
    fun createData(): List<Up> {
        return listOf(
            Up("Apple", R.drawable.apple_pic, 1000),
            Up("Banana", R.drawable.banana_pic, 2000),
            Up("Orange", R.drawable.orange_pic, 1500),
            Up("Watermelon", R.drawable.watermelon_pic, 1800)
        )
    }
}
