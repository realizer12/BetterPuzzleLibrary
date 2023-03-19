package com.example.betterandcompany.dialog

import com.example.base.base.dialog.BaseDialogFragment
import com.example.betterandcompany.R
import com.example.betterandcompany.databinding.DialogPuzzleResultBinding


/**
 * 퍼즐 결과를 알려주는 다이얼로그 프래그먼트
**/
class PuzzleResultDialogFragment:BaseDialogFragment<DialogPuzzleResultBinding>(R.layout.dialog_puzzle_result) {

    override fun DialogPuzzleResultBinding.onCreateView() {
        isCancelable = false

        initSet()
        setEventListener()
    }

    private fun initSet(){
        binding.tvDescription.text = descriptionString
    }

    private fun setEventListener(){
        binding.btnFinish.setOnClickListener {
            dismiss()
            confirmBtnClicked()
        }
    }

    companion object {

        lateinit var confirmBtnClicked: () -> Unit
        var descriptionString:String = ""

        fun getInstance(
            descriptionString: String,
            confirmBtnClicked: () -> Unit = {},
        ): PuzzleResultDialogFragment {
            this.confirmBtnClicked = confirmBtnClicked
            this.descriptionString = descriptionString
            return PuzzleResultDialogFragment()
        }
    }
}