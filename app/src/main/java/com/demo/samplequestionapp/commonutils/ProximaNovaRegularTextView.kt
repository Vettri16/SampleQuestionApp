package com.demo.samplequestionapp.commonutils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ProximaNovaRegularTextView: AppCompatTextView {

    constructor(context: Context) : super(context) {
        initTypeFace()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initTypeFace()
    }

    constructor(context: Context, attributeSet: AttributeSet, styleAttr: Int) : super(
        context,
        attributeSet,
        styleAttr
    ) {
        initTypeFace()
    }


    private fun initTypeFace() {
        val tf: Typeface = Typeface.createFromAsset(
            context.assets,
            "fonts/proxima_nova_alt_regular.otf"
        )
        this.typeface = tf
    }
}