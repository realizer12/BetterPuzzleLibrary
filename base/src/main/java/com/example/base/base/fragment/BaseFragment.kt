package com.example.base.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment<VDB: ViewDataBinding>(@LayoutRes val layoutRes: Int): Fragment(){

    lateinit var binding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.onCreateView()
        return binding.root
    }
    open fun VDB.onCreateView() = Unit

    //토스트 보여주기
    fun showToast(msg:String){
        Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()
    }

}