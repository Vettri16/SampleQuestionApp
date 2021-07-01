package com.demo.samplequestionapp.search.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchImageViewModelFactory(val application: Application): ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = SearchImageViewModel(application) as T
}