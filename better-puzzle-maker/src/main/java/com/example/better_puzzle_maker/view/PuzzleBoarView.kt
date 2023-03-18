package com.example.better_puzzle_maker.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 퍼즐 보드의 경우 grid layout manager
 * OVER_SCROLL_NEVER 모드가 고정되어야 함으로,
 * [PuzzleBoarView] 커스텀 뷰 로  위 속성을 고정시켜,
 * 개발자가  뷰사용시 해당 속성을 안 건드려도 되게끔 해준다.
**/
class PuzzleBoarView constructor(
    context: Context,
    attrs: AttributeSet? = null
):RecyclerView(context, attrs){

    init {
        this.layoutManager = GridLayoutManager(context,1)
        this.overScrollMode = OVER_SCROLL_NEVER
    }

    fun setSpanCount(spanCount:Int){
        (this.layoutManager as GridLayoutManager).spanCount = spanCount
    }
}