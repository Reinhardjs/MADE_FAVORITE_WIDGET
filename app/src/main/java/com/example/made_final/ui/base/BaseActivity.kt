package com.example.made_final.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<V : ViewModel, D : ViewDataBinding> : AppCompatActivity() {

    lateinit var viewModel: V
    lateinit var dataBinding: D

    @get:LayoutRes
    abstract val layoutRes: Int

    protected abstract fun getViewModel(): Class<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        viewModel = ViewModelProviders.of(this).get(getViewModel())
    }

}