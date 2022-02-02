package com.almuwahhid.themoviedb.resources.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.almuwahhid.themoviedb.resources.databinding.HelperBinding

abstract class BaseFragmentHelper<T: ViewBinding> : Fragment(), HelperInterface {
    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
    abstract fun observeViewModel()
    abstract fun bindingHelper(binding : T) : HelperBinding

    override fun onRefresh() = with(bindingHelper(binding)){
        noconnection.visibility = View.GONE
        servererror.visibility = View.GONE
        btnRefresh.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(binding)
        observeViewModel()
        bindingHelper(binding).btnRefresh.setOnClickListener {
            onRefresh()
        }
    }

    abstract fun initView(binding: T)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}