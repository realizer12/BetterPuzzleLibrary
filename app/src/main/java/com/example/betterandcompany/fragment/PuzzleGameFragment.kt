package com.example.betterandcompany.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.fragment.BaseFragment
import com.example.better_puzzle_maker.BetterPuzzleMaker
import com.example.better_puzzle_maker.listener.PuzzleListener
import com.example.betterandcompany.R
import com.example.betterandcompany.databinding.FragmentPuzzleGameBinding
import com.example.betterandcompany.dialog.PuzzleResultDialogFragment
import com.example.betterandcompany.viewmodel.MainViewModel

class PuzzleGameFragment : BaseFragment<FragmentPuzzleGameBinding>(R.layout.fragment_puzzle_game) {

    private lateinit var betterPuzzleMaker: BetterPuzzleMaker
    private lateinit var navController: NavController

    private val mainViewModel: MainViewModel by activityViewModels()//메인 쉐어드 뷰모델


    override fun FragmentPuzzleGameBinding.onCreateView() {
        initSet()
        setEventListener()
    }


    private fun initSet(){

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()//네비게시션 컨트롤러


        //퍼즐 메이커 세팅
        betterPuzzleMaker = BetterPuzzleMaker.Builder(
            activity = requireActivity(),
            pieceRv = binding.puzzlePieceList,
            boardRv = binding.rvPuzzleBoard,
            parentView = binding.clParent
        ).setBitmap(mainViewModel.selectedBitmap.value)
            .setPuzzlePieceCount(mainViewModel.selectedPieceCount.value ?: 1)
            .build()
    }


    private fun setEventListener(){


        //퍼즐 메이커 리스너
        betterPuzzleMaker.setOnPuzzleListener(object : PuzzleListener {
            override fun onPuzzleCompleted() {
                PuzzleResultDialogFragment.getInstance("성공 하였습니다\n축하드려요~!") {
                    requireActivity().finish()
                }.show(requireActivity().supportFragmentManager, "dialog")
            }
            override fun onPuzzleFailed() {
                PuzzleResultDialogFragment.getInstance("실패 하였습니다.\n다음에 다시 도전 해보세요!") {
                    requireActivity().finish()
                }.show(requireActivity().supportFragmentManager, "dialog")
            }
        })

        binding.ivRefresh.setOnClickListener {
            betterPuzzleMaker.refreshPuzzle()
        }


        //닫기 버튼 클릭시
        binding.ivClose.setOnClickListener {
            requireActivity().finish()
        }


        //뒤로가기 메뉴 눌렀을때
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }


}