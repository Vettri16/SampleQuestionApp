package com.demo.samplequestionapp.search.model.entities

import com.demo.samplequestionapp.base.RecyclerEntity

data class ImageUIEntity(
    override val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val isPublic: Boolean,
    val isFriend: Boolean,
    val isFamily: Boolean ): RecyclerEntity(id)