package com.demo.samplequestionapp.search.model.entities

data class SearchDataEntity(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val photo:List<ImageDataEntity>)