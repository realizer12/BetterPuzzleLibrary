package com.example.better_puzzle_maker.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.better_puzzle_maker.databinding.ItemPuzzleBoardPieceBinding
import com.example.better_puzzle_maker.model.PuzzleBoardItemModel

/**
 * 퍼즐 보드의 item 뷰
**/
internal class BoardTileVH(
    val binding:ItemPuzzleBoardPieceBinding
):ViewHolder(binding.root) {
    fun bind(puzzleBoardItemModel: PuzzleBoardItemModel){
        binding.bindingAdapterPosition = bindingAdapterPosition
        binding.puzzleBoardItemModel = puzzleBoardItemModel
    }
}