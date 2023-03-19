package com.example.betterandcompany.fragment

import androidx.core.view.drawToBitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.base.base.fragment.BaseFragment
import com.example.betterandcompany.R
import com.example.betterandcompany.databinding.FragmentImgPickBinding
import com.example.betterandcompany.util.navigateWithAnim
import com.example.betterandcompany.util.uriToBitmap
import com.example.betterandcompany.viewmodel.MainViewModel
import gun0912.tedimagepicker.builder.TedImagePicker

/**
 * 이미지 선택하는 화면 fragment
 **/
class ImagePickFragment : BaseFragment<FragmentImgPickBinding>(R.layout.fragment_img_pick) {

    //네비게이션 컨트롤러
    private lateinit var navController: NavController
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun FragmentImgPickBinding.onCreateView() {
        initSet()
        setEventListener()
        getDataFromVm()
    }

    private fun initSet() {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()//네비게시션 컨트롤러
    }

    private fun setEventListener() {

        //다음 버튼  누르면 격자 설정 화면으로 가짐.
        binding.btnNext.setOnClickListener {

            //현재 이미지 뷰에 centercrop 된 bitmap을 가지고와서 viewmodel에 넣어준다.
            mainViewModel.setBitmapImg(binding.ivImgPicker.drawToBitmap())
            if (mainViewModel.isImageSet) {
                navController.navigateWithAnim(destinationId = R.id.setGridFragment)
            } else {
                showToast(getString(R.string.choose_img))
            }
        }

        //이미지 고르기 실행
        binding.ivImgPicker.setOnClickListener {
            TedImagePicker.with(requireActivity())
                .start { uri ->
                    binding.ivImgPicker.setImageBitmap(uri.uriToBitmap(requireActivity()))
                    mainViewModel.isImageSet = true
                }
        }
    }

    private fun getDataFromVm() {
        mainViewModel.selectedBitmap.observe(viewLifecycleOwner) {
            binding.ivImgPicker.setImageBitmap(it)
        }
    }
}