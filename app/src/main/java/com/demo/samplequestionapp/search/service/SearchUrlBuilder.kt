package com.demo.samplequestionapp.search.service

import com.demo.samplequestionapp.rest.BaseUrlBuilder
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity

class SearchUrlBuilder: BaseUrlBuilder() {

    fun getSearchImageUrl(imageRequestEntity: ImageRequestEntity): String {
        var url = "$baseUrl/services/rest"
        url += "?method=${imageRequestEntity.method}"
        url += "&api_key=${imageRequestEntity.apiKey}"
        url += "&text=\"${imageRequestEntity.text}\""
        url += "&format=${imageRequestEntity.format}"
        url += "&nojsoncallback=${imageRequestEntity.noJsonCallback}"
        url += "&per_page=${imageRequestEntity.pageLimit}"
        url += "&page=${imageRequestEntity.pageOffset}"
        return url
    }

    companion object {
        var instance: SearchUrlBuilder?= null
        get() {
            return if(field==null) {
                SearchUrlBuilder()
            }else field
        }
    }

}