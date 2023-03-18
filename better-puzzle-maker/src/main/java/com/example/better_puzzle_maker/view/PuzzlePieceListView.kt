package com.example.better_puzzle_maker.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 퍼즐 조각 리스트의 경우  horizontal linearlayout 고정이므로,
 * 커스텀뷰를 만들어서 개발자가  뷰사용시 해당 속성을 안 건드려도 되게끔 해준다.
 **/
class PuzzlePieceListView constructor(
    context: Context,
    attrs: AttributeSet? = null
):RecyclerView(context, attrs){
    init {
        this.layoutManager = LinearLayoutManager(context).apply {
            orientation = HORIZONTAL
        }
    }
}

