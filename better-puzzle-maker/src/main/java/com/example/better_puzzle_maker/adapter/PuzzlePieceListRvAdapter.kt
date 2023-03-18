package com.example.better_puzzle_maker.adapter

import android.view.LayoutInflater
import android.view.View.DRAG_FLAG_OPAQUE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.better_puzzle_maker.R
import com.example.better_puzzle_maker.databinding.ItemPuzzlePieceBinding
import com.example.better_puzzle_maker.model.DragPieceModel
import com.example.better_puzzle_maker.model.DragStartingPoint
import com.example.better_puzzle_maker.model.PuzzlePieceItemModel
import com.example.better_puzzle_maker.util.ScaledDragShadow
import com.example.better_puzzle_maker.viewholder.PuzzlePieceVH


/**
 * 퍼즐 조각 리스트용 
**/
internal class PuzzlePieceListRvAdapter:ListAdapter<PuzzlePieceItemModel, PuzzlePieceVH>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzlePieceVH {
       val binding:ItemPuzzlePieceBinding =
           DataBindingUtil.inflate(
               LayoutInflater.from(parent.context),
               R.layout.item_puzzle_piece,
               parent,false
           )
        return PuzzlePieceVH(binding,this).apply {
            this.itemView.setOnLongClickListener {
                val dragShadowBuilder = ScaledDragShadow(itemView,1.2f)
                itemView.startDragAndDrop(
                   null, dragShadowBuilder, DragPieceModel(
                        dragStartingPoint = DragStartingPoint.START_FROM_PUZZLE_PIECE_LIST.ordinal,
                        itemView = itemView,
                        id = currentList[bindingAdapterPosition].id,
                        bitmapImage = currentList[bindingAdapterPosition].bitmapImage,
                   ), DRAG_FLAG_OPAQUE//불투명하게 적용
                )

                return@setOnLongClickListener true
            }
        }
    }


    override fun onBindViewHolder(holder: PuzzlePieceVH, position: Int) {
           with(holder){
               bind(currentList[bindingAdapterPosition])
               binding.executePendingBindings()
           }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PuzzlePieceItemModel>() {

            override fun areItemsTheSame(
                oldItem: PuzzlePieceItemModel,
                newItem: PuzzlePieceItemModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PuzzlePieceItemModel,
                newItem: PuzzlePieceItemModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}