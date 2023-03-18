package com.example.better_puzzle_maker.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 퍼즐 조각 Item 데이터 모델
 * @param id 퍼즐 보드 아이템들과 매칭되는  id값 - 이값을 통해 퍼즐이 맞춰졌는지 여부를 체크 한다.
 * @param bitmapImage 해당 퍼즐 조각의 bitmap 이미지
 **/
@Parcelize
internal data class PuzzlePieceItemModel(
    val id: Int,
    var bitmapImage: Bitmap?=null
): Parcelable