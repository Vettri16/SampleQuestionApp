package com.demo.samplequestionapp.search.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.demo.samplequestionapp.R
import com.demo.samplequestionapp.commonutils.Constants
import com.demo.samplequestionapp.databinding.ActSearchImagesBinding
import com.demo.samplequestionapp.search.model.SearchImageViewModel
import com.demo.samplequestionapp.search.model.SearchImageViewModelFactory

class SearchImageActivity : AppCompatActivity() {

    private lateinit var actSearchImagesBinding: ActSearchImagesBinding
    private val searchImageViewModel: SearchImageViewModel by viewModels{
        SearchImageViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actSearchImagesBinding = DataBindingUtil.setContentView(this, R.layout.act_search_images)
        if(savedInstanceState == null) {
            initImagesFragment()
        }
    }

    private fun initImagesFragment()
    {
        supportFragmentManager
            .beginTransaction()
            .add(actSearchImagesBinding.flFragContainer.id, SearchImageFragment.getCurrentInstance(null))
            .commit()
    }


}