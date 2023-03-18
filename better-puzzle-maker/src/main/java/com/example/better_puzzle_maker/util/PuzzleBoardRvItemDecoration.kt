package com.example.better_puzzle_maker.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 퍼즐 보드 item별 테두리 선을 주어, 퍼즐판 처럼 보이게 만들어준다. 
**/
internal class PuzzleBoardRvItemDecoration:RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 0
        outRect.bottom = 3
        outRect.right = 3
        outRect.left = 3
    }
}