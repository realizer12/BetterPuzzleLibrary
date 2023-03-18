package com.example.better_puzzle_maker.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.better_puzzle_maker.databinding.ItemPuzzlePieceBinding
import com.example.better_puzzle_maker.model.PuzzlePieceItemModel
import com.example.better_puzzle_maker.adapter.PuzzlePieceListRvAdapter
/**
 * 퍼즐 조각 리스트의 item view 
**/
internal class PuzzlePieceVH(
    val binding:ItemPuzzlePieceBinding,
    val adapter: PuzzlePieceListRvAdapter
): ViewHolder(binding.root) {
    fun bind(puzzlePieceItemModel: PuzzlePieceItemModel){
        itemView.visibility = View.VISIBLE
        binding.puzzlePieceItemModel = puzzlePieceItemModel
    }
}