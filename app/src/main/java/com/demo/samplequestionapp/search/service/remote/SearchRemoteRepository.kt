package com.demo.samplequestionapp.search.service.remote

import com.demo.samplequestionapp.rest.RestClient
import com.demo.samplequestionapp.rest.RestClientCallback
import com.demo.samplequestionapp.rest.RestClientEntity
import com.demo.samplequestionapp.search.model.entities.ImageRequestEntity
import com.demo.samplequestionapp.search.model.entities.SearchResultEntity
import com.demo.samplequestionapp.search.service.SearchUrlBuilder
import io.reactivex.rxjava3.core.Observable

class SearchRemoteRepository: SearchRemoteRepositoryContract {

    private val retrofit = RestClient.curInstance!!.getClient()
    private val repoService: SearchRemoteRepoService = retrofit.create(SearchRemoteRepoService::class.java)

    override fun getImagesForSearchHint(imageRequestEntity: ImageRequestEntity, tag: String): Observable<RestClientEntity<SearchResultEntity>> {
        val url = SearchUrlBuilder.instance?.getSearchImageUrl(imageRequestEntity)?: ""
        val getImageObservable = repoService.getImagesForSearchHint(url)
        val restClientCallback = RestClientCallback<SearchResultEntity>()
        return restClientCallback.enqueueCall(tag, getImageObservable)
    }
}