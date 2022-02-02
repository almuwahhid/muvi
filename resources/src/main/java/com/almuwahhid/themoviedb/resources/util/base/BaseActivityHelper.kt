package com.almuwahhid.themoviedb.resources.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.almuwahhid.themoviedb.resources.databinding.HelperBinding


abstract class BaseActivityHelper<T : ViewBinding> : AppCompatActivity(), HelperInterface {
    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val bindingInflater: (LayoutInflater) -> T
    abstract fun observeViewModel()
    abstract fun bindingHelper(binding : T) : HelperBinding
    abstract fun initView(binding: T)

    override fun onRefresh() = with(bindingHelper(binding)){
        noconnection.visibility = View.GONE
        servererror.visibility = View.GONE
        btnRefresh.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        initView(binding)
        observeViewModel()
        bindingHelper(binding).btnRefresh.setOnClickListener {
            onRefresh()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}