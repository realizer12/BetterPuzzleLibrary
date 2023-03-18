package com.example.better_puzzle_maker.model

import android.graphics.Bitmap
import android.view.View
import com.example.better_puzzle_maker.const.Const.NO_DROPPED_PUZZLE_ID

/**
 * 드래그가 시작된  지점 
**/
internal enum class DragStartingPoint{
    START_FROM_PUZZLE_PIECE_LIST,
    START_FROM_PUZZLE_BOARD
}


/**
 * 드래그 했을때 local state에 들어가는 데이터 모델 
**/
internal data class DragPieceModel(
    val dragStartingPoint:Int = DragStartingPoint.START_FROM_PUZZLE_PIECE_LIST.ordinal,
    val itemView: View,
    val id:Int,
    var droppedPuzzleItemId:Int = NO_DROPPED_PUZZLE_ID,
    var bitmapImage: Bitmap?=null
){
    companion object{

        /**
         * 퍼즈 보드에서 드래그 했을경우, true 반환
         **/
        fun DragPieceModel.isFromPuzzleBoardPiece():Boolean{
            return this.dragStartingPoint == DragStartingPoint.START_FROM_PUZZLE_BOARD.ordinal
        }


        /**
         * 퍼즐 조각 리스트드래그 했을경우, true 반환
         **/
        fun DragPieceModel.isFromPuzzlePieceList():Boolean{
            return  this.dragStartingPoint == DragStartingPoint.START_FROM_PUZZLE_PIECE_LIST.ordinal
        }

    }
}