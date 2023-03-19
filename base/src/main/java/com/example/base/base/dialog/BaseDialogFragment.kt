package com.example.base.base.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

open class BaseDialogFragment <VDB : ViewDataBinding>(@LayoutRes val layoutRes: Int) : DialogFragment() {
    lateinit var binding: VDB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.onCreateView()
        return binding.root
    }

    open fun VDB.onCreateView() = Unit
}