package com.deazzle.demo.utils

import android.view.View

interface RecyclerViewClickListener {

    fun onRecyclerViewItemClick(view: View, pos: Int)
}