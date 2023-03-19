package com.example.betterandcompany.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.activity.BaseActivity
import com.example.betterandcompany.R
import com.example.betterandcompany.databinding.ActivityPuzzleGameBinding


class PuzzleGameActivity : BaseActivity<ActivityPuzzleGameBinding>(R.layout.activity_puzzle_game) {

    //네비게이션 컨트롤러
    lateinit var navController: NavController
    override fun ActivityPuzzleGameBinding.onCreate() {
        initSet()
    }

    private fun initSet(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()//네비게시션 컨트롤러
    }
}