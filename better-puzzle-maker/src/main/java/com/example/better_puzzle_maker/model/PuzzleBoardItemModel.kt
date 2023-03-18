package com.example.better_puzzle_maker.model

import android.graphics.Bitmap
import android.os.Parcelable
import com.example.better_puzzle_maker.const.Const.NO_DROPPED_PUZZLE_ID
import kotlinx.parcelize.Parcelize

/**
 * 퍼즐 보드 Item 데이터 모델
 * @param id 퍼즐 조각 아이템들과 매칭되는  id값 - 이값을 통해 퍼즐이 맞춰졌는지 여부를 체크 한다.
 * @param droppedPuzzleId 퍼즐 조각이 drop 되어있을때 해당 하는 퍼즐 조각의 id - 이 id와 퍼즐 보드 Item의 id가 일치하면 iscorrect는 true이다.
 * @param bitmapImage 해당 퍼즐 조각의 bitmap 이미지
 * @param isCorrect 해당 퍼즐 아이템에  퍼즐 조각이 맞춰졌는지 여부  - Default 값은  false 이다.
 **/
@Parcelize
internal data class PuzzleBoardItemModel(
    val id: Int,
    var droppedPuzzleId: Int = NO_DROPPED_PUZZLE_ID,
    var bitmapImage: Bitmap?=null,
    var isCorrect:Boolean = false
): Parcelable