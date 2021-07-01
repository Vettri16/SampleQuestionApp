package com.demo.samplequestionapp.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.demo.samplequestionapp.R
import com.demo.samplequestionapp.databinding.FragImageDetailBinding
import com.demo.samplequestionapp.search.model.SearchImageViewModel
import com.demo.samplequestionapp.search.model.SearchImageViewModelFactory

class ImageDetailFragment: Fragment() {

    private lateinit var fragImageDetailBinding: FragImageDetailBinding
    private val searchImageViewModel: SearchImageViewModel by activityViewModels{
        SearchImageViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragImageDetailBinding = DataBindingUtil.inflate(inflater, R.layout.frag_image_detail, container, false)
        return fragImageDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragImageDetailBinding.viewModel = searchImageViewModel
        initCloseListener()
    }

    private fun initCloseListener()
    {
        fragImageDetailBinding.ivCloseImage.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        fun getCurrentInstance(bundle: Bundle?): ImageDetailFragment {
            val fragment = ImageDetailFragment()
            if(bundle!=null)
                fragment.arguments = bundle
            return fragment
        }
    }
}