package com.demo.samplequestionapp.search.service.local

import com.demo.samplequestionapp.rest.RestClientEntity
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity
import com.demo.samplequestionapp.search.model.entities.SearchResultEntity
import io.reactivex.rxjava3.core.Observable

interface SearchLocalRepositoryContract {

    fun getImagesForSearchHint(imageRequestEntity: ImageRequestEntity): Observable<RestClientEntity<SearchResultEntity>>
}