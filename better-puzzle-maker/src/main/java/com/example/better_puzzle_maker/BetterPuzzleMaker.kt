package com.example.better_puzzle_maker

import android.app.Activity
import android.graphics.Bitmap
import android.view.DragEvent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import com.example.better_puzzle_maker.adapter.PuzzleBoardTileRvAdapter
import com.example.better_puzzle_maker.adapter.PuzzlePieceListRvAdapter
import com.example.better_puzzle_maker.const.Const
import com.example.better_puzzle_maker.listener.PuzzleListener
import com.example.better_puzzle_maker.model.DragPieceModel.Companion.isFromPuzzleBoardPiece
import com.example.better_puzzle_maker.model.DragPieceModel.Companion.isFromPuzzlePieceList
import com.example.better_puzzle_maker.model.PuzzleBoardItemModel
import com.example.better_puzzle_maker.model.PuzzlePieceItemModel
import com.example.better_puzzle_maker.util.*
import com.example.better_puzzle_maker.view.PuzzleBoarView
import com.example.better_puzzle_maker.view.PuzzlePieceListView
import com.example.better_puzzle_maker.viewholder.BoardTileVH
import com.example.better_puzzle_maker.viewmodel.PuzzleViewModel

class BetterPuzzleMaker private constructor(
    private val activity: Activity?,
    private val boardRv: PuzzleBoarView?,
    private val pieceRv: PuzzlePieceListView?,
    private val parentView: View?,
    private var puzzleImageBitmap: Bitmap?,
    private var puzzlePieceCount: Int
) {

    private lateinit var puzzleListener: PuzzleListener

    private lateinit var imagePuzzleTileListRvAdapter: PuzzlePieceListRvAdapter //퍼즐 조각 리스트용 rv 어뎁터
    private lateinit var imagePuzzleBoardListRvAdapter: PuzzleBoardTileRvAdapter//퍼즐 보드용 rv어뎁터  - grid

    private val viewModelStoreOwner = activity as ViewModelStoreOwner

    private val lifecycleOwner = activity as LifecycleOwner

    private val puzzleViewModel: PuzzleViewModel by lazy {
        ViewModelProvider(
            owner = viewModelStoreOwner
        )[PuzzleViewModel::class.java]
    }

    fun setOnPuzzleListener(puzzleListener: PuzzleListener){
        this.puzzleListener = puzzleListener
    }

    init {
        puzzleViewModel.pieceCount = puzzlePieceCount
        setInitialPuzzleData()
        initSetPuzzleTile()
        initSetPuzzleBoard()
        setEventListener()
        getDataFromVm()
    }




    data class Builder(
        private var activity: Activity? = null,
        private var boardRv: PuzzleBoarView? = null,
        private var pieceRv: PuzzlePieceListView? = null,
        private var parentView: View? = null,
        private var puzzleImageBitmap: Bitmap? = null,
        private var puzzlePieceCount: Int = 1
    ) {
        fun activity(activity: Activity) = apply { this.activity = activity }
        fun boardRv(boardRv: PuzzleBoarView) = apply { this.boardRv = boardRv }
        fun pieceRv(pieceRv: PuzzlePieceListView) = apply { this.pieceRv = pieceRv }
        fun parentView(parentView: View) = apply { this.parentView = parentView }


        fun setBitmap(puzzleImageBitmap: Bitmap?) =
            apply { this.puzzleImageBitmap = puzzleImageBitmap }

        fun setPuzzlePieceCount(puzzlePieceCount: Int):Builder =
            apply { this.puzzlePieceCount = puzzlePieceCount }

        fun build() = BetterPuzzleMaker(
            activity,
            boardRv,
            pieceRv,
            parentView,
            puzzleImageBitmap,
            puzzlePieceCount
        )
    }


    private fun setInitialPuzzleData() {

        //퍼즐 데이터 이미 세팅 되어있으면  return 시켜줌.
        if (puzzleViewModel.isPuzzleDataAlreadySet) {
            return
        }

        if (puzzleImageBitmap == null) {
            return
        }

        puzzleImageBitmap
            ?.cutBitmapImgInPieces(pieceCount = puzzleViewModel.pieceCount)
            ?.forEachIndexed { index, bitmap ->

                puzzleViewModel.addPuzzleItemToPieceList(
                    puzzlePieceItemModel = PuzzlePieceItemModel(
                        bitmapImage = bitmap,
                        id = index
                    ), isInitialSet = true
                )

                puzzleViewModel.addPuzzleItemToBoardList(
                    puzzleBoardItemModel = PuzzleBoardItemModel(
                        id = index
                    ), isInitialSet = true
                )
            }

        puzzleViewModel.setInitialPuzzleData()
    }

    private fun initSetPuzzleTile() {
        if (pieceRv == null) {
            throw java.lang.NullPointerException()
        }

        //퍼즐 조각 리스트
        imagePuzzleTileListRvAdapter = PuzzlePieceListRvAdapter()
        pieceRv.apply {
            adapter = imagePuzzleTileListRvAdapter
        }
    }


    private fun initSetPuzzleBoard() {
        if (boardRv == null) {
            throw java.lang.NullPointerException()
        }

        //퍼즐 보드 리스트
        imagePuzzleBoardListRvAdapter = PuzzleBoardTileRvAdapter()
        boardRv.apply {
            this.setSpanCount(puzzlePieceCount)
            adapter = imagePuzzleBoardListRvAdapter
        }
        boardRv.addItemDecoration(PuzzleBoardRvItemDecoration())
    }

    private fun getDataFromVm() {
        if (pieceRv == null) {
            throw NullPointerException()
        }

        puzzleViewModel.addImagePuzzleTileListData.observe(lifecycleOwner) {
            imagePuzzleTileListRvAdapter.submitList(it.toMutableList().map { it.copy() }) {
                pieceRv.smoothScrollToPosition(0)
            }
        }
        puzzleViewModel.removeImagePuzzleTileListData.observe(lifecycleOwner) {
            imagePuzzleTileListRvAdapter.submitList(it.toMutableList().map { it.copy() })
        }

        puzzleViewModel.imagePuzzleBoardListData.observe(lifecycleOwner) {
            imagePuzzleBoardListRvAdapter.submitList(it.toMutableList().map { it.copy() })
        }

        puzzleViewModel.updatePuzzleBoardBitmapInfo.observe(
            lifecycleOwner,
            SingleEventObserver { puzzleBoardItemInfo ->

                imagePuzzleBoardListRvAdapter.currentList.find { it.id == puzzleBoardItemInfo.id }?.bitmapImage =
                    puzzleBoardItemInfo.bitmapImage

                imagePuzzleBoardListRvAdapter.currentList.find { it.id == puzzleBoardItemInfo.id }?.droppedPuzzleId =
                    puzzleBoardItemInfo.droppedPuzzleId

            })

        puzzleViewModel.puzzleAllCorrect.observe(lifecycleOwner){
            if(it){
                puzzleListener.onPuzzleCompleted()
            }else{
                puzzleListener.onPuzzleFailed()
            }
        }
    }

    private fun setEventListener() {

        if (parentView == null || boardRv == null || pieceRv == null) {
            throw NullPointerException()
        }

        parentView.setOnDragListener { _, event ->

            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {//Drag 시작시

                    //drag한 item에서 넘겨준 정보
                    val draggedPieceInfo = event.getDragAndDropPieceInfo()


                    //보드 판에서 드래그해서 온 퍼즐 조각일때 보드판의 해당 조각의 이미지뷰만 invisible 처리해줌.
                    if (draggedPieceInfo.isFromPuzzleBoardPiece()) {

                        val boardTileVH =
                            boardRv.findViewHolderForAdapterPosition(draggedPieceInfo.id) as? BoardTileVH
                        boardTileVH?.binding?.ivImageview?.visibility = View.INVISIBLE

                    } else {//퍼즐 조각 리스트에서 드래그해서 온 퍼즐 조각일때
                        draggedPieceInfo.itemView.visibility = View.INVISIBLE
                    }

                    return@setOnDragListener true
                }


                DragEvent.ACTION_DROP -> {//드래그 후 drop 했을때


                    //drag한 item에서 넘겨준 정보
                    val draggedPieceInfo = event.getDragAndDropPieceInfo()

                    if (draggedPieceInfo.isFromPuzzlePieceList()) {//퍼즐 리스트에서 드래그해서 왔을때

                        //현재 퍼즐이 drop 된 보드위치의 index를 받기위해
                        //drop 된 위치의 x와 y좌표를 보내줌.
                        val droppedBoardIndex =
                            boardRv.indexOfRvItemWithCoordinates(x = event.x, y = event.y)

                        //NO_INDEX_MATCHED_VALUE 값이면  퍼즐 보드에 drop 된게 아니므로,  다시 원래 위치로 돌아가게 해준다.
                        if (droppedBoardIndex <= Const.NO_ID_MATCHED_VALUE) {
                            return@setOnDragListener false
                        }


                        //drop한 보드 위치에 이미지가 없는 경우 -> 퍼즐 조각을 넣을수 있다.
                        if (puzzleViewModel.isPuzzleBoardItemHasNoImage(id = droppedBoardIndex)) {

                            //drop된 위치의 퍼즐 판 viewholder
                            val boardTileVH =
                                boardRv.findViewHolderForAdapterPosition(droppedBoardIndex) as? BoardTileVH

                            //drop된 퍼즐판 item에  bitmap drop 시킨 퍼즐 조간의 bitmap을 넣어줌.
                            boardTileVH?.binding?.ivImageview
                                ?.loadImage(draggedPieceInfo.bitmapImage)

                            //퍼즐 조각 리스트에서는   퍼즐 조각이 맞춰졌으니까
                            //remove 처리를 해준다.
                            puzzleViewModel.removePuzzleFromPieceList(id = draggedPieceInfo.id)

                            //퍼즐 보드 data list에도  drop 한 piece의 bitmap 정보를 넣어준다.
                            puzzleViewModel.addBitmapOnPuzzleBoardItem(
                                droppedBoarItemId = droppedBoardIndex,
                                droppedPuzzleItemId = draggedPieceInfo.id,
                                bitmap = draggedPieceInfo.bitmapImage
                            )


                            return@setOnDragListener true
                        } else {//퍼즐판에 이미 적용된 퍼즐이 있는 경우 다시 원래 위치로
                            return@setOnDragListener false
                        }


                    } else if (draggedPieceInfo.isFromPuzzleBoardPiece()) {//보드 화면에서 드래그를 했을떄

                        //보드에서 드래그 해서 퍼즐 조각 리스트 뷰에 drop했을때
                        //다시 index 0번으로 해당 이미지가 들어가야됨.
                        if (pieceRv.dragOnView(x = event.x, y = event.y)) {

                            //드래그 하기전 원래 퍼즐 판의 이미지는 없애준다.
                            (boardRv.findViewHolderForAdapterPosition(draggedPieceInfo.id) as? BoardTileVH)
                                ?.binding
                                ?.ivImageview
                                ?.loadImage(null)
                            //맨앞에 -> 해당하는 퍼즐 조각을 다시 넣어줌.
                            puzzleViewModel.addPuzzleItemToPieceList(
                                index = 0,
                                puzzlePieceItemModel = PuzzlePieceItemModel(
                                    draggedPieceInfo.droppedPuzzleItemId,
                                    draggedPieceInfo.bitmapImage
                                )
                            )

                            //퍼즐 보드 데이터의  해당하는 데이터의 bitmap 없애줌.
                            puzzleViewModel.removePuzzleBoardItemBitmap(draggedPieceInfo.id)

                            return@setOnDragListener true
                        }


                        //현재 퍼즐이 drop 된 보드위치의 index를 받기위해
                        //drop 된 위치의 x와 y좌표를 보내줌.
                        val droppedBoardIndex =
                            boardRv.indexOfRvItemWithCoordinates(x = event.x, y = event.y)

                        //dorp한 위치의 퍼즐판에 기존에 아무 bitmap도 없는 경우 ->  bitmap 적용해서 퍼즐을 맞춘 효과를 준다.
                        if (puzzleViewModel.isPuzzleBoardItemHasNoImage(id = droppedBoardIndex)) {


                            //NO_INDEX_MATCHED_VALUE 값이면  퍼즐 보드에 drop 된게 아니므로,  다시 원래 위치로 돌아가게 해준다.
                            if (droppedBoardIndex <= Const.NO_ID_MATCHED_VALUE) {
                                return@setOnDragListener false
                            }


                            //drop된 위치의 퍼즐 판 viewholder
                            val boardTileVH =
                                boardRv.findViewHolderForAdapterPosition(droppedBoardIndex) as? BoardTileVH

                            //드래그 하기전 원래 퍼즐 판 위치의ㅣ viewholder
                            val originalBoardTileVH =
                                boardRv.findViewHolderForAdapterPosition(draggedPieceInfo.id) as? BoardTileVH

                            //기존 보드판 bitmap 이미지는 Null 처리해주고, 새롭게 drop 된 위치의 보드판에 image를 넣어준다.
                            boardTileVH?.binding?.ivImageview
                                ?.loadImage(draggedPieceInfo.bitmapImage)

                            originalBoardTileVH?.binding?.ivImageview
                                ?.loadImage(null)


                            //퍼즐 조각 리스트에서는   퍼즐 조각이 맞춰졌으니까
                            //remove 처리를 해준다.
                            puzzleViewModel.removePuzzleBoardItemBitmap(draggedPieceInfo.id)

                            //퍼즐 보드 data list에도  drop 한 piece의 bitmap 정보를 넣어준다.
                            puzzleViewModel.addBitmapOnPuzzleBoardItem(
                                droppedBoarItemId = droppedBoardIndex,
                                droppedPuzzleItemId = draggedPieceInfo.droppedPuzzleItemId,
                                bitmap = draggedPieceInfo.bitmapImage
                            )

                            return@setOnDragListener true
                        } else {

                            return@setOnDragListener false
                        }

                    }
                    return@setOnDragListener false//False를 해야지 drop 했을때 다시 돌아오는 애니메이션 보임.
                }

                DragEvent.ACTION_DRAG_ENDED -> {//drag 완전히 끝남.
                    val droppedPuzzlePieceInfo = event.getDragAndDropPieceInfo()

                    if (droppedPuzzlePieceInfo.isFromPuzzlePieceList()) {
                        if (!event.result) {
                            droppedPuzzlePieceInfo.itemView.visibility = View.VISIBLE
                        }
                    } else {
                        val boardTileVH =
                            boardRv.findViewHolderForAdapterPosition(droppedPuzzlePieceInfo.id) as? BoardTileVH
                        boardTileVH?.binding?.ivImageview?.visibility = View.VISIBLE
                    }

                    true
                }

                else -> {//그밖에
                    false
                }
            }
        }
    }
}