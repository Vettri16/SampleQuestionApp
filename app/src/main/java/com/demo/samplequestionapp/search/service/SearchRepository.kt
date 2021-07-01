package com.demo.samplequestionapp.search.service

import com.demo.samplequestionapp.rest.RestClientEntity
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity
import com.demo.samplequestionapp.search.model.entities.SearchResultEntity
import com.demo.samplequestionapp.search.service.local.SearchLocalRepositoryContract
import com.demo.samplequestionapp.search.service.remote.SearchRemoteRepositoryContract
import io.reactivex.rxjava3.core.Observable

class SearchRepository(private val searchRemoteRepository: SearchRemoteRepositoryContract, private val searchLocalRepository: SearchLocalRepositoryContract) {

    var isCacheAvailable = false

    fun getImagesForGivenSearchText(imageRequestEntity: ImageRequestEntity, tag: String): Observable<RestClientEntity<SearchResultEntity>> {
        return if(!isCacheAvailable) {
            searchRemoteRepository.getImagesForSearchHint(imageRequestEntity, tag)
        } else {
            searchLocalRepository.getImagesForSearchHint(imageRequestEntity)
        }
    }
}