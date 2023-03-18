package com.example.better_puzzle_maker.util


import android.graphics.Bitmap
import android.view.DragEvent
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.better_puzzle_maker.model.DragPieceModel


/**
 * bitmap을 [pieceCount]에따라  가로 세로  잘라주고,
 * 잘린 bitmap 조각을 list로 반환 해줌.
**/
internal fun Bitmap.cutBitmapImgInPieces(pieceCount:Int): List<Bitmap> {
    val newPieces: MutableList<Bitmap> = ArrayList()
    val w = this.width
    val h = this.height

    val boxWidth = w / pieceCount
    val boxHeight = h / pieceCount


    for (i in 0 until pieceCount){
        for(j in 0 until pieceCount){
            newPieces.add(Bitmap.createBitmap(this, j * boxWidth, i*boxHeight, boxWidth, boxHeight))
        }
    }
    return newPieces
}

/**
 * 리사이클러뷰에서 현재 x , y 좌표가 어떤 index 아이템 위에 있는지를 체크해서
 * 해당 index를 리턴해준다.
**/
internal fun RecyclerView.indexOfRvItemWithCoordinates(x: Float, y: Float): Int {
    return this.children.indexOfLast {
        x in (it.x..(it.x + it.width)) && y in (it.y..(it.y + it.height))
    }
}


/**
 * 해당 파라미터로 받은 좌표가  extemsion 뷰의 위치에 있는지 여부 체크
**/
internal fun View.dragOnView(x: Float, y: Float): Boolean {
    return x in (this.x..(this.x + this.width)) && y in (this.y..(this.y + this.height))
}


/**
 *  startDragAndDrop을 할때 localState에 넘겨준 데이터인
 *  [DragPieceModel]을 dragEvent에서 뽑아서 return 해준다.
 **/
internal fun DragEvent.getDragAndDropPieceInfo(): DragPieceModel {
    return this.localState as DragPieceModel
}
