package com.demo.samplequestionapp.commonutils

import android.view.View
import com.demo.samplequestionapp.base.RecyclerEntity

interface OnAdapterClickListener<T: RecyclerEntity> {
    fun onClick(view: View, recyclerEntity: T)
}