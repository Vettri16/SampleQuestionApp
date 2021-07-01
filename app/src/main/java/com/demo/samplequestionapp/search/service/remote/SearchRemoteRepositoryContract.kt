package com.demo.samplequestionapp.search.service.remote

import com.demo.samplequestionapp.rest.RestClientEntity
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity
import com.demo.samplequestionapp.search.model.entities.SearchResultEntity
import io.reactivex.rxjava3.core.Observable

interface SearchRemoteRepositoryContract {

    fun getImagesForSearchHint(imageRequestEntity: ImageRequestEntity, tag: String): Observable<RestClientEntity<SearchResultEntity>>

}