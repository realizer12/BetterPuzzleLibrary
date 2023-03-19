package com.example.better_puzzle_maker.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.better_puzzle_maker.model.PuzzleBoardItemModel
import com.example.better_puzzle_maker.model.PuzzlePieceItemModel
import com.example.better_puzzle_maker.util.Event

/**
 * 퍼즐 관련  데이터 상태 유지를 위한 better puzzle maker
 * 전용 뷰모델
**/
internal class PuzzleViewModel:ViewModel() {

    //퍼즐 보드판 구성용 data list
    private val imagePuzzleBoardList = mutableListOf<PuzzleBoardItemModel>()

    //퍼즐 조각 목록 구성용 data list
    private val imagePuzzlePieceList = mutableListOf<PuzzlePieceItemModel>()


    //퍼즐 보드 데이터용 라이브 데이터
    private val _puzzleAllCorrect = MutableLiveData<Boolean>()
    val puzzleAllCorrect: LiveData<Boolean> = _puzzleAllCorrect

    //퍼즐 보드 데이터용 라이브 데이터
    private val _imagePuzzleBoardListData = MutableLiveData<List<PuzzleBoardItemModel>>()
    val imagePuzzleBoardListData: LiveData<List<PuzzleBoardItemModel>> = _imagePuzzleBoardListData

    //퍼즐 조각 데이터용 라이브 데이터
    private val _addImagePuzzleTileListData = MutableLiveData<List<PuzzlePieceItemModel>>()
    val addImagePuzzleTileListData: LiveData<List<PuzzlePieceItemModel>> = _addImagePuzzleTileListData

    private val _removeImagePuzzleTileListData = MutableLiveData<List<PuzzlePieceItemModel>>()
    val removeImagePuzzleTileListData: LiveData<List<PuzzlePieceItemModel>> = _removeImagePuzzleTileListData

    //퍼즐 조각 데이터용 라이브 데이터
    private val _updatePuzzleBoardBitmapInfo = MutableLiveData<Event<PuzzleBoardItemModel>>()
    val updatePuzzleBoardBitmapInfo: LiveData<Event<PuzzleBoardItemModel>> = _updatePuzzleBoardBitmapInfo

    var pieceCount = 4

    var isPuzzleDataAlreadySet: Boolean = false

    fun setInitialPuzzleData(){
         isPuzzleDataAlreadySet = true

        _imagePuzzleBoardListData.value = imagePuzzleBoardList
        _addImagePuzzleTileListData.value = imagePuzzlePieceList
    }


    /**
     * 퍼즐 조각 리스트에 퍼즐 조각 1개를  추가해줌.
    **/
    fun addPuzzleItemToPieceList(index:Int=-1,puzzlePieceItemModel: PuzzlePieceItemModel,isInitialSet:Boolean = false){
        if(index > -1){
            imagePuzzlePieceList.add(index,puzzlePieceItemModel)
        }else{
            imagePuzzlePieceList.add(puzzlePieceItemModel)
        }
        if(!isInitialSet){//초기 세팅이 아니라면, upadate를 진행한다.
            _addImagePuzzleTileListData.value = imagePuzzlePieceList
        }
    }


    /**
     * 퍼즐 조각 리스트에 해당하는 퍼즐 조각을 없애줌.
    **/
    fun removePuzzleFromPieceList(id: Int){
        imagePuzzlePieceList.removeIf {
            it.id == id
        }
        _removeImagePuzzleTileListData.value = imagePuzzlePieceList
    }

    /**
     * baord판의 퍼즐 item을 추가해준다.
    **/
    fun addPuzzleItemToBoardList(puzzleBoardItemModel: PuzzleBoardItemModel,isInitialSet:Boolean = false){
        imagePuzzleBoardList.add(puzzleBoardItemModel)
        if(!isInitialSet){//초기 세팅이 아니라면, upadate를 진행한다.
            _imagePuzzleBoardListData.value = imagePuzzleBoardList
        }
    }


    fun removePuzzleBoardItemBitmap(droppedBoarItemId:Int){
        imagePuzzleBoardList.find { it.id == droppedBoarItemId}?.bitmapImage = null
        imagePuzzleBoardList.find { it.id == droppedBoarItemId}?.droppedPuzzleId = -1
        _updatePuzzleBoardBitmapInfo.value = Event(PuzzleBoardItemModel(droppedPuzzleId = -1, id = droppedBoarItemId, bitmapImage = null))
    }

    /**
     * 퍼즐 보드판에 해당하는 idnex의 판에 이미 이미지가 적용되어있는지 여부 체크
    **/
    fun isPuzzleBoardItemHasNoImage(id:Int):Boolean{
       return imagePuzzleBoardList.find { it.id == id}?.bitmapImage == null
    }

    fun addBitmapOnPuzzleBoardItem(droppedBoarItemId:Int,droppedPuzzleItemId:Int,bitmap: Bitmap?){
        with(imagePuzzleBoardList.find { it.id == droppedBoarItemId}){
            this?.bitmapImage = bitmap
            this?.droppedPuzzleId = droppedPuzzleItemId
            this?.isCorrect = this?.id == droppedPuzzleItemId
        }

        if(imagePuzzleBoardList.all { it.bitmapImage != null }){
            _puzzleAllCorrect.value = imagePuzzleBoardList.all { it.isCorrect }
        }

        _updatePuzzleBoardBitmapInfo.value = Event(PuzzleBoardItemModel(id = droppedBoarItemId, droppedPuzzleId = droppedPuzzleItemId,bitmapImage = bitmap))
    }

}