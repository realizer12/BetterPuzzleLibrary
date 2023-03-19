package com.example.betterandcompany.fragment

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.fragment.BaseFragment
import com.example.betterandcompany.R
import com.example.betterandcompany.databinding.FragmentSetPuzzleBinding
import com.example.betterandcompany.util.navigateWithAnim
import com.example.betterandcompany.viewmodel.MainViewModel


/**
 * 퍼즐 격자 크기  설정 화면용 fragment
 **/
class SetPuzzlePieceFragment : BaseFragment<FragmentSetPuzzleBinding>(R.layout.fragment_set_puzzle) {

    //네비게이션 컨트롤러
    lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()//메인 쉐어드 뷰모델


    override fun FragmentSetPuzzleBinding.onCreateView() {
        initSet()
        getDataFromVm()
        setEventListener()
    }

    private fun initSet() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()//네비게시션 컨트롤러
    }



    private fun setEventListener() {

        //조각 변경시
        binding.editPieceCount.doAfterTextChanged {
            if(!it.isNullOrEmpty()){
              mainViewModel.setPieceCount(it.toString().toInt())
            }
        }

        //이미지 피커 화면으로 돌아기
        binding.ivBack.setOnClickListener {
            navController.popBackStack()
        }

        //퍼즐 시작하기
        binding.btnStartPuzzle.setOnClickListener {
            navController.navigateWithAnim(R.id.puzzleGameFragment)
        }
    }

    private fun getDataFromVm(){
        mainViewModel.selectedPieceCount.observe(requireActivity()) {
            binding.tvRowCount.text = it.toString()
            binding.tvColumnCount.text = it.toString()
        }
    }
}