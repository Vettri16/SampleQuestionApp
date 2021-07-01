package com.demo.samplequestionapp.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.demo.samplequestionapp.commonutils.OnAdapterClickListener
import com.demo.samplequestionapp.commonutils.SpaceItemDecoration
import com.demo.samplequestionapp.search.model.entities.ImageUIEntity


open class RecyclerEntity(open val id: String) {

    lateinit var clickListener: OnAdapterClickListener<*>

    companion object {

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(view: ImageView, imageEntity: ImageUIEntity) {
            val url = "https://farm"+imageEntity.farm+".staticflickr.com/"+imageEntity.server+"/"+imageEntity.id+"_"+imageEntity.secret+"_m.jpg"
            Glide.with(view.context).load(url)
                .apply(RequestOptions())
                .addListener(object :
                    RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        // Load placeholder
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }).into(view)
        }

        @JvmStatic
        @BindingAdapter("itemDecor")
        fun itemDecor(view: RecyclerView, value: Int) {
            view.addItemDecoration(SpaceItemDecoration(value))
        }
    }

}