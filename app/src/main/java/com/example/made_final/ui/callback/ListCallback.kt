package com.example.made_final.ui.callback

import android.widget.ImageView

interface ListCallback<T> {
    fun onItemClicked(imageView: ImageView, movieEntity: T)
}
