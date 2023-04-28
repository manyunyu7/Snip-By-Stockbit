package com.feylabs.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<B : ViewBinding> : Fragment() {


    abstract fun initUI()
    abstract fun initObserver()
    abstract fun initAction()
    abstract fun initData()


    private var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val bindingClass =
            (this::class.java.genericSuperclass as? ParameterizedType)?.actualTypeArguments.takeIf { it?.isNotEmpty() == true }
                ?.get(0) as? Class<*>
        val inflateMethod = bindingClass?.getMethod("inflate", LayoutInflater::class.java)
        (inflateMethod?.invoke(null, layoutInflater) as? B)?.also { _binding = it }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}