package com.feylabs.core.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<
        VB : ViewBinding,
//        VM : BaseViewModel
        >(
    private val bindingInflater: (LayoutInflater) -> VB
) : Fragment() {

    private var _binding: VB? = null
    open val binding: VB
        get() = _binding as VB

    abstract fun initUI()
    abstract fun initObserver()
    abstract fun initAction()
    abstract fun initData()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater)
        _binding?.let {
            return it.root
        } ?: throw IllegalArgumentException("Binding variable is null")
    }
}
