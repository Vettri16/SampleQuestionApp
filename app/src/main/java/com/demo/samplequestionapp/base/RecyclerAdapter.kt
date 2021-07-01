package com.demo.samplequestionapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.demo.samplequestionapp.commonutils.OnAdapterClickListener

class RecyclerAdapter<T : RecyclerEntity, R: ViewDataBinding>(private val context: Context, private val activityBinding: R, private val adapterVariable: Int, private val layoutId: Int, private val entityVariable: Int, private var imageEntities: List<T>, private val onAdapterClickListener: OnAdapterClickListener<T>) : RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {

    init {
        activityBinding.setVariable(adapterVariable, this)
        imageEntities.forEach { it.clickListener = onAdapterClickListener }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val viewBinding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(context),layoutId, parent, false)
        return RecyclerViewHolder(viewBinding)
    }

    override fun getItemCount(): Int = imageEntities.size


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.viewBinding.setVariable(entityVariable, imageEntities[position])
    }

    fun setItems(items: List<T>) {
        val diffSize = items.size - imageEntities.size
        imageEntities = items
        imageEntities.forEach { it.clickListener = onAdapterClickListener }
        if(diffSize > 0) {
            notifyItemRangeInserted(items.size - diffSize, diffSize)
        } else {
            notifyDataSetChanged()
        }
    }

    class RecyclerViewHolder(val viewBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewBinding.root)


}