package com.example.better_puzzle_maker.listener

/**
 * 퍼즐 관련 리스너
 * 현재는 성공 실패 콜백만 존재.
 *
**/
interface PuzzleListener {

    /**
     *  퍼즐 맞추기 성공시 콜백
    **/
    fun onPuzzleCompleted()

    /**
     * 퍼즐 맞추기 실패시 콜백
    **/
    fun onPuzzleFailed()
}