package com.example.betterandcompany.activity

import android.content.Intent
import com.example.base.base.activity.BaseActivity
import com.example.betterandcompany.R
import com.example.betterandcompany.databinding.ActivityMainBinding

class MainActivity:BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun ActivityMainBinding.onCreate() {

        //퍼즐 게임 화면으로 이동
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this@MainActivity,PuzzleGameActivity::class.java))
        }
    }
}