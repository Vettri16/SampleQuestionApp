package com.demo.samplequestionapp.search.model.entities

import com.demo.samplequestionapp.base.UIEntity

data class SearchUIEntity(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val photo:List<ImageUIEntity>): UIEntity