package com.demo.samplequestionapp.search.model.entities

data class ImageRequestEntity(
    val method: String,
    val apiKey: String,
    val text: String,
    val format: String,
    val noJsonCallback: Int,
    val pageOffset: Int,
    val pageLimit: Int)