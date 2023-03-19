package com.example.betterandcompany.viewmodel

import android.graphics.Bitmap
import androidx.core.view.drawToBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * mainactivity위에  Fragment들이 share 하는 뷰모델 
**/
class MainViewModel:ViewModel() {


    private val _selectedBitmap = MutableLiveData<Bitmap>()
    val selectedBitmap: LiveData<Bitmap> = _selectedBitmap

    private val _selectedPieceCount = MutableLiveData<Int>(1)
    val selectedPieceCount: LiveData<Int> = _selectedPieceCount

    //이미지 세팅 되어있는지 여부 체크
    var isImageSet = false

    fun setBitmapImg(bitmap: Bitmap){
        _selectedBitmap.value = bitmap
    }

    fun setPieceCount(pieceCount:Int){
        _selectedPieceCount.value = pieceCount
    }

}