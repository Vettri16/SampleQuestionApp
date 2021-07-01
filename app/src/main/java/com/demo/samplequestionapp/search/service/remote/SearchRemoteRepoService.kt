package com.demo.samplequestionapp.search.service.remote

import com.demo.samplequestionapp.search.model.entities.SearchResultEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface SearchRemoteRepoService {

    @GET
    fun getImagesForSearchHint(@Url url: String): Single<Response<SearchResultEntity>>

}