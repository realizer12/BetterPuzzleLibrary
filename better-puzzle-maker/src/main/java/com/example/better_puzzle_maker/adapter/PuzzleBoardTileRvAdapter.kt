package com.example.better_puzzle_maker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.better_puzzle_maker.R
import com.example.better_puzzle_maker.databinding.ItemPuzzleBoardPieceBinding
import com.example.better_puzzle_maker.model.DragPieceModel
import com.example.better_puzzle_maker.model.DragStartingPoint
import com.example.better_puzzle_maker.model.PuzzleBoardItemModel
import com.example.better_puzzle_maker.util.ScaledDragShadow
import com.example.better_puzzle_maker.viewholder.BoardTileVH


/**
 * 퍼즐 보드 리스트 용
**/
internal class PuzzleBoardTileRvAdapter:ListAdapter<PuzzleBoardItemModel, BoardTileVH>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardTileVH {
        val binding: ItemPuzzleBoardPieceBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_puzzle_board_piece,
                parent,false
            )
        return BoardTileVH(binding).apply {
            this.itemView.setOnLongClickListener {

                //퍼즐 사진이 올라와있는 경우가 아니라면 일반 판이므로, false return -아무 효과 없게.
                if(this.binding.ivImageview.drawable == null){
                    return@setOnLongClickListener false
                }

                val dragShadowBuilder = ScaledDragShadow(itemView,1.2f)

                itemView.startDragAndDrop(
                    null, dragShadowBuilder, DragPieceModel(
                        dragStartingPoint =  DragStartingPoint.START_FROM_PUZZLE_BOARD.ordinal,
                        itemView = itemView,
                        droppedPuzzleItemId=currentList[bindingAdapterPosition].droppedPuzzleId,
                        id = currentList[bindingAdapterPosition].id,
                        bitmapImage = currentList[bindingAdapterPosition].bitmapImage,
                    ), View.DRAG_FLAG_OPAQUE//불투명하게 적용
                )

                return@setOnLongClickListener true
            }
        }
    }

    override fun onBindViewHolder(holder: BoardTileVH, position: Int) {
        with(holder){
            bind(currentList[bindingAdapterPosition])
            binding.executePendingBindings()
        }
    }



    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PuzzleBoardItemModel>() {

            override fun areItemsTheSame(
                oldItem: PuzzleBoardItemModel,
                newItem: PuzzleBoardItemModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PuzzleBoardItemModel,
                newItem: PuzzleBoardItemModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}